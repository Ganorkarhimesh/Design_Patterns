# Railway Reservation System — Design Patterns Mini Project  

A single Java console application implementing **7 design patterns** together
in one coherent domain (railway ticket booking), backed by a real **SQLite**
database (`railway.db`, created automatically on first run).

## How to run

Requires JDK 11+ (tested on OpenJDK 21).

```bash
./run.sh
```

or manually:

```bash
mkdir -p bin
javac -cp lib/sqlite-jdbc-3.40.1.0.jar -d bin $(find src -name "*.java")
java -cp "bin:lib/sqlite-jdbc-3.40.1.0.jar" main.Main
```

This compiles everything, runs `main.Main`, creates `railway.db` in the
project folder, and prints the contents of the `bookings` and
`notification_log` tables at the end so you can see the persistence working.

---

## 1. Singleton Pattern

**Package:** `singleton`
**Class:** `DatabaseConnectionManager`

Ensures only **one** SQLite connection exists for the whole application.
Private constructor + static `getInstance()` + a static instance field,
with double-checked locking for thread safety. Every other pattern
(Proxy, Observer) reads/writes through this same instance so there's never a
duplicate or conflicting connection to `railway.db`.

```java
DatabaseConnectionManager db1 = DatabaseConnectionManager.getInstance();
DatabaseConnectionManager db2 = DatabaseConnectionManager.getInstance();
// db1 == db2  -> true
```

---

## 2. Chain of Responsibility Pattern

**Package:** `chain`
**Classes:**
- `ValidationHandler` (abstract Handler)
- `PassengerDetailsHandler` → `SeatAvailabilityHandler` → `PaymentValidationHandler` (ConcreteHandlers)

Each handler checks one thing about a `Booking` and either passes it to
`nextHandler` or rejects it outright (returns `false`, stopping the chain).
The chain is built once, inside `ReservationServiceProxy`:

```java
passengerCheck.setNext(seatCheck).setNext(paymentCheck);
```

---

## 3. Factory Method Pattern

**Package:** `factorymethod`

| Role | Class(es) |
|---|---|
| Product | `Ticket` (interface) |
| ConcreteProduct | `GeneralTicket`, `SleeperTicket`, `ACTicket`, `TatkalTicket` |
| Creator | `TicketCreator` (abstract, declares `createTicket()`) |
| ConcreteCreator | `GeneralTicketCreator`, `SleeperTicketCreator`, `ACTicketCreator`, `TatkalTicketCreator` |

Each `ConcreteCreator` overrides `createTicket()` to decide which concrete
`Ticket` gets built. `TicketCreator.issueTicket()` is the shared logic
(print ticket + compute surcharge) that stays identical across all
creators — only *which product* varies.

`TicketCreatorFactory` is a small convenience **Simple Factory** (not the
pattern itself) used only so `Main` doesn't need `if/else` to pick a creator.

---

## 4. Proxy Pattern

**Package:** `proxy`

| Role | Class |
|---|---|
| Subject (interface) | `ReservationService` |
| RealSubject | `RealReservationService` — inserts the booking into SQLite |
| Proxy | `ReservationServiceProxy` — adds validation (via the Chain of Responsibility), logging, and post-success Observer notification, before delegating to the RealSubject |
| Client | `Main` |

The client only ever talks to `ReservationServiceProxy`, never directly to
`RealReservationService`. The proxy decides whether the real object is
even invoked — e.g., invalid bookings are rejected before the database is
touched at all.

---

## 5. Abstract Factory Pattern

**Package:** `abstractfactory`

| Role | Class(es) |
|---|---|
| AbstractFactory | `TravelClassFactory` |
| ConcreteFactory | `SleeperClassFactory`, `ACClassFactory`, `GeneralClassFactory` |
| AbstractProduct A | `SeatAmenity` |
| AbstractProduct B | `MealService` |

Each concrete factory produces a **matched family** of objects — you can
never accidentally get an AC meal paired with a Sleeper seat amenity,
because both come from the same factory call.

---

## 6. Bridge Pattern

**Package:** `bridge`

| Role | Class(es) |
|---|---|
| Abstraction | `Payment` |
| RefinedAbstraction | `FastTrackPayment` (adds a Tatkal-style priority fee) |
| Implementor | `PaymentGateway` |
| ConcreteImplementor | `UPIGateway`, `CreditCardGateway`, `NetBankingGateway` |

`Payment` holds a `PaymentGateway` reference (the "bridge") instead of
inheriting from it, so payment types and gateway types can each grow
independently — e.g. adding a `WalletGateway` never requires touching
`Payment` or `FastTrackPayment`.

---

## 7. Observer Pattern (with database connectivity)

**Package:** `observer`

| Role | Class(es) |
|---|---|
| Subject | `ReservationSubject` |
| Observer (interface) | `ReservationObserver` — `update(Booking booking)` |
| ConcreteObserver | `PassengerAppObserver`, `SMSNotificationObserver`, `EmailNotificationObserver` |

`update()` takes the **`Booking` object itself**, not a String — this
mirrors the correction your instructor gave on the Faculty Leave
Management System practical. `SMSNotificationObserver` and
`EmailNotificationObserver` also **write their own row** into a
`notification_log` table via the same Singleton connection, so the
Observer pattern here is genuinely tied to database connectivity, not
just console prints.

---

## Database schema (auto-created)

```sql
CREATE TABLE bookings (
    pnr INTEGER PRIMARY KEY AUTOINCREMENT,
    passenger_name TEXT, age INTEGER, gender TEXT,
    mobile TEXT, email TEXT,
    train_name TEXT, source TEXT, destination TEXT,
    travel_class TEXT, payment_mode TEXT, fare REAL, status TEXT
);

CREATE TABLE notification_log (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    pnr INTEGER, channel TEXT, message TEXT,
    sent_at TEXT DEFAULT CURRENT_TIMESTAMP
);
```

## Sample flow exercised in `Main.java`

1. Get the Singleton DB manager twice, prove it's the same instance.
2. Attach 3 observers to a `ReservationSubject`.
3. Wrap a `RealReservationService` in a `ReservationServiceProxy`.
4. Submit a valid AC booking → passes the chain → persisted → all 3 observers fire.
5. Submit a deliberately invalid booking (blank name, negative age) → rejected by `PassengerDetailsHandler`, chain stops, nothing is written to the DB.
6. Submit a valid Sleeper booking → same success path.
7. Use `TicketCreatorFactory` + `TicketCreator` (Factory Method) to issue tickets and compute class-based surcharges.
8. Use `TravelClassFactory` (Abstract Factory) to get matched seat/meal amenities for AC and Sleeper.
9. Use `Payment`/`FastTrackPayment` (Bridge) with `UPIGateway`/`CreditCardGateway` to process the final fares.
10. Dump `bookings` and `notification_log` tables to prove everything actually persisted.
