package com.example.healthcare_system.servicetest;

import com.example.healthcare_system.entity.Patient;
import com.example.healthcare_system.repo.PatientRepository;
import com.example.healthcare_system.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Test
    void testGetAllPatients() {
        when(patientRepository.findAll()).thenReturn(List.of(new Patient(
                "",  // medicalHistory (empty for now)
                LocalDate.now().minusYears(28), // dateOfBirth (calculated from age)
                "Female",  // phone (assuming "Female" was mistakenly placed instead of a phone number)
                "jane@example.com", // email
                "Doe",  // lastName
                "Jane", // firstName
                2L      // id
        )));
        List<Patient> patients = patientService.getAllPatients();
        assertFalse(patients.isEmpty());
    }
}
