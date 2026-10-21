# Smart Hospital Management System — MVC + Swing + SQLite

A Java 17 desktop application demonstrating MVC architecture, Swing forms, JDBC, SQLite persistence, CRUD operations, and basic JUnit tests.

## Features
- Patient registration, listing, editing, and deletion
- Appointment creation and listing, linked to a patient
- Bill creation and listing, linked to a patient
- Treatment-record creation and listing, linked to a patient
- SQLite database initialized automatically at `data/hospital.db`
- Prepared statements for database operations and basic input validation

## Requirements
- JDK 17 or newer
- Maven 3.8+ (downloads the JDBC and test dependencies on first run)

## Run
From this directory:

```bash
mvn clean test
mvn exec:java
```

The database and tables are created automatically on launch. The first launch creates `data/hospital.db` in the project directory. To reset sample/test data, close the app and delete that database file.

## MVC structure
- `model/`: data objects and validation-oriented domain types
- `dao/`: JDBC persistence (SQL stays out of Swing components)
- `controller/`: coordinates actions between the view and DAOs
- `view/`: Swing tabs, forms, and table refresh
- `util/`: connection factory and schema initialization

## Database tables
`patients`, `appointments`, `bills`, `treatment_records`. Foreign keys link the three clinical/administrative records to patients.

## Notes
- Dates use ISO format: `YYYY-MM-DD`; appointment time uses `HH:MM`.
- This is an educational starter project, not a production clinical system. Before real use, add authentication, role-based access, audit logs, backups, encryption, stronger validation, and applicable privacy/compliance controls.
