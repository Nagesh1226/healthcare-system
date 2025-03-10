package com.example.healthcare_system.controller;

import com.example.healthcare_system.entity.Patient;
import com.example.healthcare_system.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PatientController.class)
public class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    void setUp() {
        patient1 = new Patient(
                "",  // medicalHistory (empty for now)
                LocalDate.now().minusYears(28), // dateOfBirth (calculated from age)
                "Female",  // phone (assuming "Female" was mistakenly placed instead of a phone number)
                "jane@example.com", // email
                "Doe",  // lastName
                "Jane", // firstName
                2L      // id
        );

        patient2 = new Patient(
                "",  // medicalHistory (empty for now)
                LocalDate.now().minusYears(28), // dateOfBirth (calculated from age)
                "Female",  // phone (assuming "Female" was mistakenly placed instead of a phone number)
                "jane@example.com", // email
                "Doe",  // lastName
                "Jane", // firstName
                2L      // id
        );
    }

    @Test
    void testGetAllPatients() throws Exception {
        List<Patient> patients = Arrays.asList(patient1, patient2);
        when(patientService.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }

    @Test
    void testGetPatientById() throws Exception {
        when(patientService.getPatientById(1L)).thenReturn(patient1);

        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetPatientById_NotFound() throws Exception {
        when(patientService.getPatientById(3L)).thenThrow(new PatientNotFoundException("Patient not found"));

        mockMvc.perform(get("/api/patients/3"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient not found"));
    }

    @Test
    void testCreatePatient() throws Exception {
        when(patientService.createPatient(any(Patient.class))).thenReturn(patient1);

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testCreatePatient_InvalidInput() throws Exception {
        Patient invalidPatient = new Patient(
                "",  // medicalHistory (empty for now)
                LocalDate.now().minusYears(28), // dateOfBirth (calculated from age)
                "Female",  // phone (assuming "Female" was mistakenly placed instead of a phone number)
                "jane@example.com", // email
                "Doe",  // lastName
                "Jane", // firstName
                -1L      // id
        );

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPatient)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdatePatient() throws Exception {
        when(patientService.updatePatient(Mockito.eq(1L), any(Patient.class))).thenReturn(patient1);

        mockMvc.perform(put("/api/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testUpdatePatient_NotFound() throws Exception {
        when(patientService.updatePatient(Mockito.eq(3L), any(Patient.class)))
                .thenThrow(new PatientNotFoundException("Patient not found"));

        mockMvc.perform(put("/api/patients/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient not found"));
    }

    @Test
    void testDeletePatient() throws Exception {
        doNothing().when(patientService).deletePatient(1L);

        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeletePatient_NotFound() throws Exception {
        Mockito.doThrow(new PatientNotFoundException("Patient not found")).when(patientService).deletePatient(3L);

        mockMvc.perform(delete("/api/patients/3"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient not found"));
    }
}
