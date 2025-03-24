package com.bs.interfaces;

import java.util.List;

import com.bs.model.Doctor;

public interface IDoctorDAO {
	//Retrieves all patients from database
	public List<Doctor> selectAllDoctors();
	
	//Retrieves a specific doctor based on their ID
	public Doctor selectDoctor(int doctor_id);
	
	//Inserts a new doctor record into the database
	public void insertDoctor(Doctor doctor);
	
	//Updates an existing doctor record in the database
	public void updateDoctor(Doctor doctor);
	
	//Deletes a doctor record from the database based on their ID
	public void deleteDoctor(int doctor_id);
}
