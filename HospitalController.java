package com.smarthospital.controller;

import com.smarthospital.dao.*;
import com.smarthospital.model.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class HospitalController {
    private final PatientDao patients;
    private final AppointmentDao appointments;
    private final BillDao bills;
    private final TreatmentDao treatments;

    public HospitalController(PatientDao p,AppointmentDao a,BillDao b,TreatmentDao t){
        patients=p;appointments=a;bills=b;treatments=t;
    }
    public List<Patient> patients() throws SQLException{return patients.findAll();}
    public int savePatient(int id,String name,String age,String gender,String phone,String disease)throws Exception{
        if(name.isBlank()||gender.isBlank()||phone.isBlank()||disease.isBlank())throw new IllegalArgumentException("All patient fields are required.");
        int n;
        try{n=Integer.parseInt(age);}catch(NumberFormatException e){throw new IllegalArgumentException("Age must be a whole number.");}
        if(n<0||n>130)throw new IllegalArgumentException("Age must be between 0 and 130.");
        Patient p=new Patient(id,name.trim(),n,gender,phone.trim(),disease.trim());
        if(id>0){if(!patients.update(p))throw new IllegalArgumentException("Patient no longer exists.");return id;}
        return patients.insert(p);
    }
    public boolean deletePatient(int id)throws SQLException{return patients.delete(id);}
    public List<Appointment> appointments()throws SQLException{return appointments.findAll();}
    public void addAppointment(String patient,String date,String time,String doctor,String reason)throws Exception{
        int id=patientId(patient); validDate(date); validTime(time);
        required(doctor,"Doctor");required(reason,"Reason");
        appointments.insert(id,date,time,doctor.trim(),reason.trim());
    }
    public List<Bill> bills()throws SQLException{return bills.findAll();}
    public void addBill(String patient,String date,String description,String amount,String status)throws Exception{
        int id=patientId(patient);validDate(date);required(description,"Description");
        double value;try{value=Double.parseDouble(amount);}catch(NumberFormatException e){throw new IllegalArgumentException("Amount must be numeric.");}
        if(!Double.isFinite(value)||value<0)throw new IllegalArgumentException("Amount must be zero or greater.");
        bills.insert(id,date,description.trim(),value,status);
    }
    public List<TreatmentRecord> treatments()throws SQLException{return treatments.findAll();}
    public void addTreatment(String patient,String date,String diagnosis,String treatment,String notes)throws Exception{
        int id=patientId(patient);validDate(date);required(diagnosis,"Diagnosis");required(treatment,"Treatment");required(notes,"Notes");
        treatments.insert(id,date,diagnosis.trim(),treatment.trim(),notes.trim());
    }
    private int patientId(String value){
        try{int id=Integer.parseInt(value.substring(0,value.indexOf(" - ")));if(id<1)throw new NumberFormatException();return id;}
        catch(Exception e){throw new IllegalArgumentException("Select a valid patient.");}
    }
    private void validDate(String d){try{LocalDate.parse(d);}catch(DateTimeParseException e){throw new IllegalArgumentException("Date must use YYYY-MM-DD.");}}
    private void validTime(String t){try{LocalTime.parse(t);}catch(DateTimeParseException e){throw new IllegalArgumentException("Time must use HH:MM (24-hour format).");}}
    private void required(String s,String field){if(s==null||s.isBlank())throw new IllegalArgumentException(field+" is required.");}
}
