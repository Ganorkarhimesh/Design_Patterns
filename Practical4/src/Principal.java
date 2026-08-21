class Principal extends LeaveApprover {

    public void approveLeave(LeaveRequest request){

        if(request.getDays()>2 && request.getDays()<=7){

            System.out.println(request.getFacultyName()
                    +" leave approved by Principal");

        }

        else{

            nextApprover.approveLeave(request);

        }

    }

}