package com.bs.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.bs.model.Doctor;

class DoctorDAOTest {
	
	private DoctorDAO doctorDAO = new DoctorDAO();
	
	// Positive test case: Insert a valid doctor and check if inserted correctly
	@Test
    public void testInsertDoctorSuccess() {
        
        Doctor doctor = new Doctor(0, "Dr. Hasara", "Eye Specialisit", "0713688985", "City Hospital", 2500.0);
        doctorDAO.insertDoctor(doctor);
        List<Doctor> doctors = doctorDAO.selectAllDoctors();
        // Check if the newly inserted doctor is in the list
        assertTrue(doctors.stream().anyMatch(d -> d.getDoctor_name().equals("Dr. Hasara")));
    }

	// Positive test case: Retrieve all doctors and check if the list is not empty
    @Test
    public void testSelectAllDoctorsSuccess() {
        List<Doctor> doctors = doctorDAO.selectAllDoctors();
        // Assert that there are doctors in the database
        assertFalse(doctors.isEmpty()); // Assuming there are doctors in the database
    }

    // Positive test case: Select a doctor by ID and verify the details
    @Test
    public void testSelectDoctorByIdSuccess() {
        Doctor doctor = doctorDAO.selectDoctor(3); // Assume a doctor with ID 3 exists
        
        assertNotNull(doctor); // Ensure the doctor is not null
        assertEquals(3, doctor.getDoctor_id()); // Verify the ID
        assertEquals("Dr. Samantha Perera", doctor.getDoctor_name());
    }

    // Positive test case: Update an existing doctor's details and verify the update
    @Test
    public void testUpdateDoctorSuccess() {
        
        Doctor doctor = new Doctor(20, "Dr. Hasara", "Pediatrics", "0987654321", "City Hospital", 2000.0);
        
        doctorDAO.updateDoctor(doctor);
        Doctor updatedDoctor = doctorDAO.selectDoctor(20); // Fetch updated doctor
        
        assertEquals("Dr. Hasara", updatedDoctor.getDoctor_name()); // Verify the updated name
        assertEquals("Pediatrics", updatedDoctor.getSpecialization()); // Verify the updated specialization
    }

    // Negative test case: Delete a doctor and ensure the doctor is no longer in the database
    @Test
    public void testDeleteDoctorUnsuccess() {
        
        doctorDAO.deleteDoctor(1); // Assume a doctor with ID 1 not exists
        Doctor doctor = doctorDAO.selectDoctor(1); // Try to retrieve the doctor
        assertNotNull(doctor); // Doctor should not be null
    }

    // Negative test case: Update a doctor with a non-existent ID
    @Test
    public void testUpdateDoctorNonExistentId() {
        // Create a Doctor object with a non-existent ID
        Doctor doctor = new Doctor(999, "Jane Doe", "Neurology", "0123456789", "City Hospital", 250.0);
        
        // Attempt to update the doctor
        doctorDAO.updateDoctor(doctor);
        
        // Assert that the doctor with the non-existent ID does not exist in the database
        Doctor updatedDoctor = doctorDAO.selectDoctor(999); // Adjust if necessary
        assertNull(updatedDoctor, "Doctor with ID 999 should not exist in the database after update attempt.");
    }

}
