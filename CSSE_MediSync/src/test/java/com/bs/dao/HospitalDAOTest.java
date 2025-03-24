package com.bs.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.bs.model.Hospital;

class HospitalDAOTest {
	
	// Positive Test Case : Select All Hospitals
	@Test
	void testSelectAllHospitals() {
	    HospitalDAO hospitalDAO = new HospitalDAO();
	    List<Hospital> hospitals = hospitalDAO.selectAllHospitals();
	    
	    // Assert that the list is not empty (assuming there are records in DB)
	    assertNotNull(hospitals);
	    assertTrue(hospitals.size() > 0);
	}

	//Positive test case : Select Specific Hospital
	@Test
	void testSelectHospitalById() {
	    HospitalDAO hospitalDAO = new HospitalDAO();
	    Hospital hospital = hospitalDAO.selectHospital(1); // Assuming hospital with id=1 exists
	    
	    // Assert that the hospital is retrieved
	    assertNotNull(hospital);
	    assertEquals(1, hospital.getHospital_id());
	}
	
	//Negative test case : Delete Non-Existing Hospital
	@Test
	void testDeleteNonExistingHospital() {
	    HospitalDAO hospitalDAO = new HospitalDAO();
	    int hospital_id = 9999;

	    // Assert that the hospital does not exist before delete
	    assertNull(hospitalDAO.selectHospital(hospital_id), "Hospital should not exist before delete");

	    // Perform delete and assert hospital still does not exist
	    hospitalDAO.deleteHospital(hospital_id);
	    assertNull(hospitalDAO.selectHospital(hospital_id), "Hospital should not exist after delete");
	}

	
	//Negative test case : Select Hospital with Invalid ID
	@Test
	void testSelectHospitalWithInvalidId() {
	    HospitalDAO hospitalDAO = new HospitalDAO();
	    Hospital hospital = hospitalDAO.selectHospital(9999); // Assuming id=9999 doesn't exist

	    // Assert that no hospital is returned
	    assertNull(hospital);
	}

}
