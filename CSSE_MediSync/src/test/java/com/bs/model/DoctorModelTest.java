package com.bs.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DoctorModelTest {
	// Positive Test: Creating a Doctor object
    @Test
    void testDoctorObjectCreation() {
        Doctor doctor = new Doctor();
        doctor.setDoctor_name("John Doe");
        doctor.setSpecialization("Cardiology");
        doctor.setContact_no("1234567890");
        doctor.setHospital_name("City Hospital");
        doctor.setDoctor_charge(150.0);

        // Verify that the doctor object has the expected values
        assertEquals("John Doe", doctor.getDoctor_name(), "Doctor name should be set correctly");
        assertEquals("Cardiology", doctor.getSpecialization(), "Specialization should be set correctly");
        assertEquals("1234567890", doctor.getContact_no(), "Contact number should be set correctly");
        assertEquals("City Hospital", doctor.getHospital_name(), "Hospital name should be set correctly");
        assertEquals(150.0, doctor.getDoctor_charge(), "Doctor charge should be set correctly");
    }

    // Positive Test: Testing Getters and Setters
    @Test
    void testGettersAndSetters() {
        Doctor doctor = new Doctor();
        doctor.setDoctor_name("Jane Smith");
        doctor.setSpecialization("Neurology");
        doctor.setContact_no("0987654321");
        doctor.setHospital_name("General Hospital");
        doctor.setDoctor_charge(200.0);
        
        assertEquals("Jane Smith", doctor.getDoctor_name(), "Doctor name should be Jane Smith");
        assertEquals("Neurology", doctor.getSpecialization(), "Specialization should be Neurology");
        assertEquals("0987654321", doctor.getContact_no(), "Contact number should be 0987654321");
        assertEquals("General Hospital", doctor.getHospital_name(), "Hospital name should be General Hospital");
        assertEquals(200.0, doctor.getDoctor_charge(), "Doctor charge should be 200.0");
    }

    // Negative Test: Setting invalid contact number
    @Test
    void testSetInvalidContactNumber() {
        Doctor doctor = new Doctor();
        doctor.setContact_no("invalid_contact"); // Setting an invalid contact number
        
        assertEquals("invalid_contact", doctor.getContact_no(), "Contact number should be set to invalid_contact");
        
        // This assertion checks that the doctor object has set the contact number to the invalid value
        assertNotEquals("1234567890", doctor.getContact_no(), "Contact number should not be valid");
        
        // Test if the doctor object does not contain any non-numeric characters (additional check)
        assertFalse(doctor.getContact_no().matches("\\d+"), "Contact number should contain only digits");
    }

    // Negative Test: Setting negative doctor charge
    @Test
    void testSetNegativeDoctorCharge() {
        Doctor doctor = new Doctor();
        doctor.setDoctor_charge(-100.0); // Setting a negative charge
        
        assertEquals(-100.0, doctor.getDoctor_charge(), "Doctor charge should be set to -100.0");
        assertTrue(doctor.getDoctor_charge() < 0, "Doctor charge should be negative");
        
        // Check that the charge is not a valid charge (additional check)
        assertNotEquals(0.0, doctor.getDoctor_charge(), "Doctor charge should not be zero");
    }

}
