package com.smarthospital.dao;

import com.smarthospital.model.Appointment;
import com.smarthospital.util.Database;
import java.sql.*;
import java.util.*;

public class AppointmentDao {
    public void insert(int patientId,String date,String time,String doctor,String reason)throws SQLException{
        String q="INSERT INTO appointments(patient_id,appointment_date,appointment_time,doctor,reason) VALUES(?,?,?,?,?)";
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(q)){
            p.setInt(1,patientId);p.setString(2,date);p.setString(3,time);p.setString(4,doctor);p.setString(5,reason);p.executeUpdate();
        }
    }
    public List<Appointment> findAll()throws SQLException{
        List<Appointment> out=new ArrayList<>();
        String q="SELECT a.*,p.name patient_name FROM appointments a JOIN patients p ON p.id=a.patient_id ORDER BY a.appointment_date DESC,a.appointment_time";
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(q);ResultSet r=p.executeQuery()){
            while(r.next())out.add(new Appointment(r.getInt("id"),r.getInt("patient_id"),r.getString("patient_name"),r.getString("appointment_date"),r.getString("appointment_time"),r.getString("doctor"),r.getString("reason"),r.getString("status")));
        }return out;
    }
}
