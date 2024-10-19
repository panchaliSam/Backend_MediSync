package com.bs.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.bs.model.Patient;

class PatientDAOTest {
    private PatientDAO patientDAO = new PatientDAO();

    // Positive Test
    @Test
    void testSelectAllPatients_Positive() {
        Patient newPatient = new Patient(0, " Doe", 30, "1993-01-01", "1234567890", "0987654321", "Brother", "None", 1);
        patientDAO.insertPatient(newPatient, 1);

        List<Patient> patients = patientDAO.selectAllPatients();
        
        // Simpler assertion to verify if the patient is inserted correctly
        assertEquals("Doe", patients.get(patients.size() - 1).getPatient_name());
    }

    // Negative Test: Verify that no patients are returned when the list is empty.
    @Test
    void testSelectAllPatients_Negative(){
        List<Patient> patients = patientDAO.selectAllPatients();
        assertEquals(0, patients.size()); // Expecting an empty list
    }

    // Negative Test: Verify that selecting a non-existing patient ID returns null.
    @Test
    void testSelectPatient_Negative(){
        Patient patient = patientDAO.selectPatient(99); // ID that does not exist
        assertNull(patient); // Expecting null when no patient is found
    }
    
 // Positive Test: Insert a patient, delete them, and verify they no longer exist.
    @Test
    void testDeletePatient(){
        
        patientDAO.insertPatient(new Patient(0, "Charlie White", 35, "1988-04-25", "1234567890", "0987654321", "Sister", "None", 1), 1);
        
        // Fetch the patient list to find the inserted patient's ID
        List<Patient> patients = patientDAO.selectAllPatients();
        int patientId = patients.get(patients.size() - 1).getUser_id(); // Get the last inserted patient's ID
        
        // Delete the patient
        patientDAO.deletePatient(patientId); // Using the fetched ID
        
        Patient retrievedPatient = patientDAO.selectPatient(patientId);
        assertNull(retrievedPatient); // Expecting null after deletion
    }
}
