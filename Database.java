package com.smarthospital.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public final class Database {
    private static final String URL = "jdbc:sqlite:data/hospital.db";
    private Database() {}

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException ex) {
            connection.close();
            throw ex;
        }
        return connection;
    }

    public static void initialize() throws Exception {
        Files.createDirectories(Path.of("data"));
        try (Connection c = getConnection(); Statement s = c.createStatement()) {
            s.execute("PRAGMA foreign_keys = ON");
            s.executeUpdate("""
                CREATE TABLE IF NOT EXISTS patients (
                  id INTEGER PRIMARY KEY AUTOINCREMENT,
                  name TEXT NOT NULL, age INTEGER NOT NULL CHECK(age BETWEEN 0 AND 130),
                  gender TEXT NOT NULL, phone TEXT NOT NULL, disease TEXT NOT NULL,
                  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
                )""");
            s.executeUpdate("""
                CREATE TABLE IF NOT EXISTS appointments (
                  id INTEGER PRIMARY KEY AUTOINCREMENT,
                  patient_id INTEGER NOT NULL, appointment_date TEXT NOT NULL,
                  appointment_time TEXT NOT NULL, doctor TEXT NOT NULL, reason TEXT NOT NULL,
                  status TEXT NOT NULL DEFAULT 'Scheduled',
                  FOREIGN KEY(patient_id) REFERENCES patients(id) ON DELETE CASCADE
                )""");
            s.executeUpdate("""
                CREATE TABLE IF NOT EXISTS bills (
                  id INTEGER PRIMARY KEY AUTOINCREMENT,
                  patient_id INTEGER NOT NULL, bill_date TEXT NOT NULL,
                  description TEXT NOT NULL, amount REAL NOT NULL CHECK(amount >= 0),
                  payment_status TEXT NOT NULL DEFAULT 'Unpaid',
                  FOREIGN KEY(patient_id) REFERENCES patients(id) ON DELETE CASCADE
                )""");
            s.executeUpdate("""
                CREATE TABLE IF NOT EXISTS treatment_records (
                  id INTEGER PRIMARY KEY AUTOINCREMENT,
                  patient_id INTEGER NOT NULL, record_date TEXT NOT NULL,
                  diagnosis TEXT NOT NULL, treatment TEXT NOT NULL, notes TEXT NOT NULL,
                  FOREIGN KEY(patient_id) REFERENCES patients(id) ON DELETE CASCADE
                )""");
        }
    }
}
