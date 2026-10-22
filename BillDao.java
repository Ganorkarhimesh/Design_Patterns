package com.smarthospital.dao;

import com.smarthospital.model.Bill;
import com.smarthospital.util.Database;
import java.sql.*;
import java.util.*;

public class BillDao {
    public void insert(int patientId,String date,String description,double amount,String status)throws SQLException{
        String q="INSERT INTO bills(patient_id,bill_date,description,amount,payment_status) VALUES(?,?,?,?,?)";
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(q)){
            p.setInt(1,patientId);p.setString(2,date);p.setString(3,description);p.setDouble(4,amount);p.setString(5,status);p.executeUpdate();
        }
    }
    public List<Bill> findAll()throws SQLException{
        List<Bill> out=new ArrayList<>();
        String q="SELECT b.*,p.name patient_name FROM bills b JOIN patients p ON p.id=b.patient_id ORDER BY b.bill_date DESC";
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(q);ResultSet r=p.executeQuery()){
            while(r.next())out.add(new Bill(r.getInt("id"),r.getInt("patient_id"),r.getString("patient_name"),r.getString("bill_date"),r.getString("description"),r.getDouble("amount"),r.getString("payment_status")));
        }return out;
    }
}
