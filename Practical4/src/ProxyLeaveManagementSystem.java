class ProxyLeaveManagementSystem implements LeaveManagementService {

    private String username;
    private String password;

    RealLeaveManagementSystem real =
            new RealLeaveManagementSystem();

    public ProxyLeaveManagementSystem(String username,String password){

        this.username=username;
        this.password=password;
    }

    private boolean authenticate(){

        return username.equals("admin") &&
               password.equals("1234");
    }

    public void submitLeave(LeaveRequest request){

        if(authenticate()){

            System.out.println("Login Successful");

            real.submitLeave(request);

        }

        else{

            System.out.println("Invalid Username or Password");
        }

    }

}