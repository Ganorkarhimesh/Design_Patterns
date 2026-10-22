package com.smarthospital.model;

public record Bill(int id, int patientId, String patientName, String date,
                   String description, double amount, String paymentStatus) {}
