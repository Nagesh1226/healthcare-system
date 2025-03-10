package com.example.healthcare_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    private LocalDate dateOfBirth;

    @Column(length = 500)
    private String medicalHistory;

    public Patient(String medicalHistory, LocalDate dateOfBirth, String phone, String email, String lastName, String firstName, Long id) {
        this.medicalHistory = medicalHistory;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
    }
}
