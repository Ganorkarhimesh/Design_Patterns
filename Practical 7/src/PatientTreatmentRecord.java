import java.util.Stack;

public class TreatmentHistory {

    private Stack<TreatmentMemento> history = new Stack<>();

    public void save(TreatmentMemento memento) {
        history.push(memento);
    }

    public TreatmentMemento undo() {
        if (!history.isEmpty()) {
            return history.pop();
        }

        return null;
    }
}