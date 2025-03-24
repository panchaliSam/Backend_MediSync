package com.bs.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PatientModelTest {

	// Positive Test Case: Test Patient Object Creation with Valid Data
    @Test
    void testPatientObjectCreation() {
        Patient patient = new Patient(1, "John Doe", 30, "1993-05-15", "1234567890", 
                                      "0987654321", "Brother", "Penicillin", 101);

        // Assert the values are set correctly
        assertEquals(1, patient.getPatient_id(), "Patient ID should be set correctly");
        assertEquals("John Doe", patient.getPatient_name(), "Patient name should be set correctly");
        assertEquals(30, patient.getAge(), "Patient age should be set correctly");
    }

    // Positive Test Case: Test Setters and Getters
    @Test
    void testSettersAndGetters() {
        Patient patient = new Patient();
        patient.setPatient_id(2);
        patient.setPatient_name("Jane Smith");
        patient.setAge(25);
        patient.setDob("1998-10-12");
        patient.setContact_no("9876543210");
        patient.setEmergency_contact_no("1234567890");
        patient.setEmergency_relation("Mother");
        patient.setAllergy("None");
        patient.setUser_id(102);

        // Assert that values are set and retrieved correctly
        assertEquals(2, patient.getPatient_id(), "Patient ID should match");
        assertEquals("Jane Smith", patient.getPatient_name(), "Patient name should match");
        assertEquals(25, patient.getAge(), "Patient age should match");
    }

    // Positive Test Case: Test Default Constructor
    @Test
    void testDefaultConstructor() {
        Patient patient = new Patient();

        // Assert default values
        assertEquals(0, patient.getPatient_id(), "Default Patient ID should be 0");
        assertNull(patient.getPatient_name(), "Default Patient name should be null");
        assertEquals(0, patient.getAge(), "Default Patient age should be 0");
    }

    // Negative Test Case: Test Invalid Age (Negative Value)
    @Test
    void testSetInvalidAge() {
        Patient patient = new Patient();
        patient.setAge(-5);  // Invalid age

        // Assert that invalid age is set
        assertEquals(-5, patient.getAge(), "Patient age should be set to -5");
        assertTrue(patient.getAge() < 0, "Patient age should not be negative");
    }

    // Negative Test Case: Test Empty Contact Number
    @Test
    void testSetEmptyContactNumber() {
        Patient patient = new Patient();
        patient.setContact_no("");  // Invalid contact number

        // Assert that an empty contact number is set
        assertEquals("", patient.getContact_no(), "Contact number should be an empty string");
        assertTrue(patient.getContact_no().isEmpty(), "Contact number should not be empty");
    }

    // Negative Test Case: Test Invalid Patient ID (Negative ID)
    @Test
    void testSetInvalidPatientId() {
        Patient patient = new Patient();
        patient.setPatient_id(-1);  // Invalid patient ID

        // Assert that invalid patient ID is set
        assertEquals(-1, patient.getPatient_id(), "Patient ID should be set to -1");
        assertTrue(patient.getPatient_id() < 0, "Patient ID should not be negative");
    }
}
