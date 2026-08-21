abstract class LeaveApprover {

    protected LeaveApprover nextApprover;

    public void setNextApprover(LeaveApprover next){

        nextApprover=next;
    }

    public abstract void approveLeave(LeaveRequest request);

}