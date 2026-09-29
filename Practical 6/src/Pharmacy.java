public class Pharmacy extends Department {

    public Pharmacy(Mediator mediator) {
        super(mediator, "Pharmacy");
    }

    public void provideMedicine(String medicine) {
        sendMessage("Medicine provided: " + medicine + ".");
    }
}
