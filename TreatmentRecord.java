package com.smarthospital.model;

public record TreatmentRecord(int id, int patientId, String patientName, String date,
                              String diagnosis, String treatment, String notes) {}
