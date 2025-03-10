package com.example.healthcare_system.controller;

public class PatientNotFoundException extends Throwable {
    public PatientNotFoundException(String patientNotFound) {
        super(patientNotFound);
    }
}
