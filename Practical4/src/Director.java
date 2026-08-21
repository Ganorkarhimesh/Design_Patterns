class Director extends LeaveApprover {

    public void approveLeave(LeaveRequest request){

        System.out.println(request.getFacultyName()
                +" leave approved by Director");

    }

}