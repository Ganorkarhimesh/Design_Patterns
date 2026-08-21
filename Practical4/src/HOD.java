class HOD extends LeaveApprover {

    public void approveLeave(LeaveRequest request){

        if(request.getDays()<=2){

            System.out.println(request.getFacultyName()
                    +" leave approved by HOD");

        }

        else{

            nextApprover.approveLeave(request);

        }

    }

}