package com.smarthospital.model;

public record Appointment(int id, int patientId, String patientName, String date,
                          String time, String doctor, String reason, String status) {}
