package com.bs.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bs.model.PatientRecord;

class PatientRecordDAOTest {
    
    PatientRecordDAO patientRecordDAO;
    PatientRecord existingPatientRecord; // Existing record reference

    @BeforeEach
    public void setUp() {
        patientRecordDAO = new PatientRecordDAO();
        
        // Assuming an existing record with ID 1 for testing
        existingPatientRecord = patientRecordDAO.selectPatientRecord(21); // Fetching an existing record
        assertNotNull(existingPatientRecord); // Ensure the record exists
    }

    @AfterEach
    public void tearDown() {
        // Cleanup logic if necessary (not needed in this case)
    }

    // Test case to verify that all patient records can be retrieved successfully
    @Test
    void testSelectAllPatientRecords() {
        List<PatientRecord> records = patientRecordDAO.selectAllPatientRecords();
        assertNotNull(records); // Ensure the result is not null
        assertTrue(records.size() > 0); // Check that there are records in the database
    }

    // Test case to verify retrieving a patient record by a valid ID
    @Test
    void testSelectPatientRecordValidId() {
        PatientRecord record = patientRecordDAO.selectPatientRecord(existingPatientRecord.getRecord_id());
        assertNotNull(record); // Ensure the record is found
        assertEquals(existingPatientRecord.getRecord_id(), record.getRecord_id()); // Verify that the correct record is returned
    }

    // Test case to verify that attempting to retrieve a patient record with an invalid ID returns null
    @Test
    void testSelectPatientRecordInvalidId() {
        int invalidRecordId = -1; // Assuming this ID does not exist
        PatientRecord record = patientRecordDAO.selectPatientRecord(invalidRecordId);
        assertNull(record); // Expecting null since the ID is invalid
    }

    // Test case to verify that an existing patient record can be updated
    @Test
    void testUpdatePatientRecord() {
        // Update the existing patient record
        existingPatientRecord.setDiagnosis("Updated Diagnosis"); // Update the diagnosis
        patientRecordDAO.updatePatientRecord(existingPatientRecord); // Save the changes

        // Verify the update
        PatientRecord updatedRecord = patientRecordDAO.selectPatientRecord(existingPatientRecord.getRecord_id());
        assertEquals("Updated Diagnosis", updatedRecord.getDiagnosis()); // Check that the update was successful
    }

    // Test case to verify that a patient record can be deleted
    @Test
    void testDeletePatientRecord() {
        int recordIdToDelete = existingPatientRecord.getRecord_id(); // Use the ID of the existing record
        patientRecordDAO.deletePatientRecord(recordIdToDelete); // Delete the record

        // Verify deletion
        PatientRecord deletedRecord = patientRecordDAO.selectPatientRecord(recordIdToDelete);
        assertNull(deletedRecord); // Ensure the record is no longer found
    }
}
