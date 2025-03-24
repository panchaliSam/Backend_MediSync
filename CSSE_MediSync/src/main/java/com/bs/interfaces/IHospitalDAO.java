package com.bs.interfaces;

import java.util.List;

import com.bs.model.Hospital;

public interface IHospitalDAO {
	
		//Retrieves all hospitals from database
		public List<Hospital> selectAllHospitals();
			
		//Retrieves a specific hospital based on their ID
		public Hospital selectHospital(int hospital_id);
			
		//Inserts a new hospital record into the database
		public void insertHospital(Hospital hospital, int userId);
			
		//Updates an existing hospital record in the database
		public void updateHospital(Hospital hospital);
			
		//Deletes a hospital record from the database based on their ID
		public void deleteHospital(int hospital_id);
}
