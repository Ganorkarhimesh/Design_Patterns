package com.smarthospital.dao;

import com.smarthospital.model.Patient;
import com.smarthospital.util.Database;
import java.sql.*;
import java.util.*;

public class PatientDao {
    public List<Patient> findAll() throws SQLException {
        List<Patient> list = new ArrayList<>();
        try (Connection c=Database.getConnection();
             PreparedStatement p=c.prepareStatement("SELECT * FROM patients ORDER BY id DESC");
             ResultSet r=p.executeQuery()) {
            while(r.next()) list.add(map(r));
        }
        return list;
    }
    public Optional<Patient> findById(int id) throws SQLException {
        try(Connection c=Database.getConnection();
            PreparedStatement p=c.prepareStatement("SELECT * FROM patients WHERE id=?")) {
            p.setInt(1,id);
            try(ResultSet r=p.executeQuery()){return r.next()?Optional.of(map(r)):Optional.empty();}
        }
    }
    public int insert(Patient x) throws SQLException {
        String sql="INSERT INTO patients(name,age,gender,phone,disease) VALUES(?,?,?,?,?)";
        try(Connection c=Database.getConnection(); PreparedStatement p=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            bind(p,x); p.executeUpdate();
            try(ResultSet r=p.getGeneratedKeys()){return r.next()?r.getInt(1):lastId(c);}
        }
    }
    private int lastId(Connection c) throws SQLException {try(Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT last_insert_rowid()")){r.next();return r.getInt(1);}}
    public boolean update(Patient x) throws SQLException {
        String sql="UPDATE patients SET name=?,age=?,gender=?,phone=?,disease=? WHERE id=?";
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            bind(p,x);p.setInt(6,x.getId());return p.executeUpdate()>0;
        }
    }
    public boolean delete(int id) throws SQLException {
        try(Connection c=Database.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM patients WHERE id=?")){
            p.setInt(1,id);return p.executeUpdate()>0;
        }
    }
    private void bind(PreparedStatement p,Patient x)throws SQLException{
        p.setString(1,x.getName());p.setInt(2,x.getAge());p.setString(3,x.getGender());
        p.setString(4,x.getPhone());p.setString(5,x.getDisease());
    }
    private Patient map(ResultSet r)throws SQLException{
        return new Patient(r.getInt("id"),r.getString("name"),r.getInt("age"),
            r.getString("gender"),r.getString("phone"),r.getString("disease"));
    }
}
