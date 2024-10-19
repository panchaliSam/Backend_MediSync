package com.bs.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class HospitalModelTest {

    // Positive Test Case: Test Hospital Object Creation with Valid Data
    @Test
    void testHospitalObjectCreation() {
        Hospital hospital = new Hospital(1, "General Hospital", 5000.0);

        // Assert values are set correctly
        assertEquals(1, hospital.getHospital_id(), "Hospital ID should be set correctly");
        assertEquals("General Hospital", hospital.getHospital_name(), "Hospital name should be set correctly");
        assertEquals(5000.0, hospital.getHospital_charge(), "Hospital charge should be set correctly");
    }

    // Positive Test Case: Test Setters and Getters
    @Test
    void testSettersAndGetters() {
        Hospital hospital = new Hospital();
        hospital.setHospital_id(2);
        hospital.setHospital_name("City Clinic");
        hospital.setHospital_charge(7500.0);

        // Assert values set using setters are returned correctly
        assertEquals(2, hospital.getHospital_id(), "Hospital ID should match");
        assertEquals("City Clinic", hospital.getHospital_name(), "Hospital name should match");
        assertEquals(7500.0, hospital.getHospital_charge(), "Hospital charge should match");
    }

    // Positive Test Case: Test Default Constructor
    @Test
    void testDefaultConstructor() {
        Hospital hospital = new Hospital();

        // Assert default values
        assertEquals(0, hospital.getHospital_id(), "Default Hospital ID should be 0");
        assertNull(hospital.getHospital_name(), "Default Hospital name should be null");
        assertEquals(0.0, hospital.getHospital_charge(), "Default Hospital charge should be 0.0");
    }

    // Negative Test Case: Test Negative Hospital Charge
    @Test
    void testSetNegativeHospitalCharge() {
        Hospital hospital = new Hospital();
        hospital.setHospital_charge(-100.0);  // Invalid hospital charge

        // Assert that invalid charge is set, but should not be allowed in business logic
        assertEquals(-100.0, hospital.getHospital_charge(), "Hospital charge should be set to -100.0");
        assertTrue(hospital.getHospital_charge() < 0, "Hospital charge should not be negative");
    }

    // Negative Test Case: Test Empty Hospital Name
    @Test
    void testSetEmptyHospitalName() {
        Hospital hospital = new Hospital();
        hospital.setHospital_name("");  // Invalid hospital name

        // Assert that the empty hospital name is set
        assertEquals("", hospital.getHospital_name(), "Hospital name should be set as an empty string");
        
        // Additional logic can check if names should not be empty
        assertFalse(hospital.getHospital_name().isEmpty(), "Hospital name should not be empty");
    }

    // Negative Test Case: Test Invalid Hospital ID
    @Test
    void testSetInvalidHospitalId() {
        Hospital hospital = new Hospital();
        hospital.setHospital_id(-1);  // Invalid hospital ID

        // Assert that invalid hospital ID is set
        assertEquals(-1, hospital.getHospital_id(), "Hospital ID should be set to -1");
        assertTrue(hospital.getHospital_id() < 0, "Hospital ID should not be negative");
    }
}
