class LeaveFactory {

    public LeaveRequest createLeave(String type, String name, int days) {

        if(type.equalsIgnoreCase("CL"))
            return new CasualLeave(name, days);

        else if(type.equalsIgnoreCase("ML"))
            return new MedicalLeave(name, days);

        else if(type.equalsIgnoreCase("OD"))
            return new OnDutyLeave(name, days);

        else
            return null;
    }
}