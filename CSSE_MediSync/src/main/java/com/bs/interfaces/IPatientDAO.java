package com.bs.interfaces;

import java.util.List;

import com.bs.model.Patient;

public interface IPatientDAO {
	
	//Retrieves all patients from database
	public List<Patient> selectAllPatients();
		
	//Retrieves a specific patient based on their ID
	public Patient selectPatient(int patient_id);
		
	//Inserts a new patient record into the database
	public void insertPatient(Patient patient);
		
	//Updates an existing patient record in the database
	public void updatePatient(Patient patient);
		
	//Deletes a patient record from the database based on their ID
	public void deletePatient(int patient_id);
}
