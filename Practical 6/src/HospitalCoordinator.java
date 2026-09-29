import java.util.ArrayList;
import java.util.List;

public class HospitalCoordinator implements Mediator {

    private List<Department> departments = new ArrayList<>();

    public void registerDepartment(Department department) {
        departments.add(department);
    }

    @Override
    public void sendMessage(String message, Department sender) {
        for (Department department : departments) {
            if (department != sender) {
                department.receiveMessage(message);
            }
        }
    }
}
