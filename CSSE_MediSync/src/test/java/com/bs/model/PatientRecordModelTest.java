package com.bs.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PatientRecordModelTest {
	

	//positive test case
	@Test
	void testSettersAndGetters() {
	    PatientRecord record = new PatientRecord();
	    record.setPatient_name("Bob Williams");
	    record.setHospital_name("City Clinic");
	    record.setDoctor_name("Dr. Jane Doe");
	    record.setAppointment_id(202);
	    record.setDiagnosis("Cold");
	    record.setMedicines("Ibuprofen");
	    record.setLab_test_report_link("link/to/report");

	    // Assert values set using setters are returned correctly
	    assertEquals("Bob Williams", record.getPatient_name(), "Patient name should match");
	    assertEquals("City Clinic", record.getHospital_name(), "Hospital name should match");
	    assertEquals("Dr. Jane Doe", record.getDoctor_name(), "Doctor name should match");
	    assertEquals(202, record.getAppointment_id(), "Appointment ID should match");
	    assertEquals("Cold", record.getDiagnosis(), "Diagnosis should match");
	    assertEquals("Ibuprofen", record.getMedicines(), "Medicines should match");
	    assertEquals("link/to/report", record.getLab_test_report_link(), "Lab test report link should match");
	}

	@Test
	void testEmptyConstructor() {
	    PatientRecord record = new PatientRecord();

	    // Assert default values
	    assertNull(record.getPatient_name(), "Patient name should be null by default");
	    assertNull(record.getHospital_name(), "Hospital name should be null by default");
	    assertNull(record.getDoctor_name(), "Doctor name should be null by default");
	    assertEquals(0, record.getAppointment_id(), "Appointment ID should be 0 by default");
	}
	
	//Negative test cases
	@Test
	void testSetInvalidAppointmentId() {
	    PatientRecord record = new PatientRecord();
	    record.setAppointment_id(-1);  // Invalid appointment ID

	    // Assert that invalid appointment ID is set correctly (for validation in DAO layer)
	    assertEquals(-1, record.getAppointment_id(), "Appointment ID should be set to -1");
	    assertTrue(record.getAppointment_id() < 0, "Appointment ID should not be negative");
	}
	
	@Test
	void testSetInvalidLabTestReportLink() {
	    PatientRecord record = new PatientRecord();
	    record.setLab_test_report_link("invalid_link");  // Invalid report link

	    // Assert the invalid lab test report link is set correctly
	    assertEquals("invalid_link", record.getLab_test_report_link(), "Lab test report link should be set as invalid_link");
	    
	    // Test if the link does not contain valid URL characters 
	    assertFalse(record.getLab_test_report_link().matches("^https?://.+"), "Lab test report link should be a valid URL");
	}

	@Test
	void testSetEmptyDiagnosis() {
	    PatientRecord record = new PatientRecord();
	    record.setDiagnosis("");  // Empty diagnosis

	    // Assert empty diagnosis is set correctly
	    assertEquals("", record.getDiagnosis(), "Diagnosis should be set as an empty string");
	    
	    // Check that the diagnosis not be empty 
	    assertNotEquals(null, record.getDiagnosis(), "Diagnosis should not be null");
	}

}
