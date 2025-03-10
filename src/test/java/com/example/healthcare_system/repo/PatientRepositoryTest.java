package com.example.healthcare_system.repo;

import com.example.healthcare_system.entity.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PatientRepositoryTest {
    @Autowired
    private PatientRepository patientRepository;

    @Test
    void testSaveAndFindPatient() {
        Patient patient = new Patient(
                "",  // medicalHistory (empty for now)
                LocalDate.now().minusYears(28), // dateOfBirth (calculated from age)
                "Female",  // phone (assuming "Female" was mistakenly placed instead of a phone number)
                "jane@example.com", // email
                "Doe",  // lastName
                "Jane", // firstName
                2L      // id
        );
        Patient savedPatient = patientRepository.save(patient);

        Optional<Patient> retrievedPatient = patientRepository.findById(savedPatient.getId());
        assertTrue(retrievedPatient.isPresent());
        assertEquals("John", retrievedPatient.get().getFirstName());
    }
}
