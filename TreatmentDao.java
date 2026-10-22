package com.smarthospital.dao;

import com.smarthospital.model.TreatmentRecord;
import com.smarthospital.util.Database;
import java.sql.*;
import java.util.*;

public class TreatmentDao {
    public void insert(int patientId,String date,String diagnosis,String treatment,String notes)throws SQLException{
        String q="INSERT INTO treatment_records(patient_id,record_date,diagnosis,treatment,notes) VALUES(?,?,?,?,?)";
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(q)){
            p.setInt(1,patientId);p.setString(2,date);p.setString(3,diagnosis);p.setString(4,treatment);p.setString(5,notes);p.executeUpdate();
        }
    }
    public List<TreatmentRecord> findAll()throws SQLException{
        List<TreatmentRecord> out=new ArrayList<>();
        String q="SELECT t.*,p.name patient_name FROM treatment_records t JOIN patients p ON p.id=t.patient_id ORDER BY t.record_date DESC";
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(q);ResultSet r=p.executeQuery()){
            while(r.next())out.add(new TreatmentRecord(r.getInt("id"),r.getInt("patient_id"),r.getString("patient_name"),r.getString("record_date"),r.getString("diagnosis"),r.getString("treatment"),r.getString("notes")));
        }return out;
    }
}
