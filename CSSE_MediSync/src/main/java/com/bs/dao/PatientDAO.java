package com.bs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bs.interfaces.IPatientDAO;
import com.bs.model.Patient;
import com.bs.utility.DBConnection;

public class PatientDAO implements IPatientDAO {
	// SQL Queries for Patient operations
	String SELECT_PATIENT_BY_ID = "SELECT * FROM patient WHERE patient_id = ?";
	
    String SELECT_ALL_PATIENTS = "SELECT * FROM patient";
    
    String INSERT_PATIENT = "INSERT INTO patient (patient_name, age, dob, contact_no, emergency_contact_no, emergency_relation, allergy, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    String UPDATE_PATIENT = "UPDATE patient SET patient_name=?, age=?, dob=?, contact_no=?, emergency_contact_no=?, emergency_relation=?, allergy=? WHERE patient_id = ?";
    
    String DELETE_PATIENT = "DELETE FROM patient WHERE patient_id = ?";
	
	@Override
	public List<Patient> selectAllPatients() {
		// TODO Auto-generated method stub
		List<Patient> patients = new ArrayList<>();
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_ALL_PATIENTS);
			 ResultSet rs = stmt.executeQuery()) {
			
			while (rs.next()) {
                int patient_id = rs.getInt("patient_id");
                String patient_name = rs.getString("patient_name");
                int age = rs.getInt("age");
                String dob = rs.getString("dob");
                String contact_no = rs.getString("contact_no");
                String emergency_contact_no = rs.getString("emergency_contact_no");
                String emergency_relation = rs.getString("emergency_relation");
                String allergy = rs.getString("allergy");
                int user_id = rs.getInt("user_id");
                
                Patient patient = new Patient(patient_id, patient_name, age, dob, contact_no, emergency_contact_no, emergency_relation, allergy, user_id);
                patients.add(patient);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patients;
	}
	@Override
	public Patient selectPatient(int patient_id) {
		// TODO Auto-generated method stub
		Patient patient = new Patient();
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_PATIENT_BY_ID)) {
			
			stmt.setInt(1, patient_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String patient_name = rs.getString("patient_name");
                    int age = rs.getInt("age");
                    String dob = rs.getString("dob");
                    String contact_no = rs.getString("contact_no");
                    String emergency_contact_no = rs.getString("emergency_contact_no");
                    String emergency_relation = rs.getString("emergency_relation");
                    String allergy = rs.getString("allergy");
                    int user_id = rs.getInt("user_id");

                    patient = new Patient(patient_id, patient_name, age, dob, contact_no, emergency_contact_no, emergency_relation, allergy, user_id);
                }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patient;
	}
	@Override
	public void insertPatient(Patient patient, int userId) {
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement stmt = con.prepareStatement(INSERT_PATIENT)) {
	        stmt.setString(1, patient.getPatient_name());
	        stmt.setInt(2, patient.getAge());
	        stmt.setString(3, patient.getDob());
	        stmt.setString(4, patient.getContact_no());
	        stmt.setString(5, patient.getEmergency_contact_no());
	        stmt.setString(6, patient.getEmergency_relation());
	        stmt.setString(7, patient.getAllergy());
	        stmt.setInt(8, userId); 
	        stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    @Override
    public void updatePatient(Patient patient) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_PATIENT)) {

            stmt.setString(1, patient.getPatient_name());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getDob());
            stmt.setString(4, patient.getContact_no());
            stmt.setString(5, patient.getEmergency_contact_no());
            stmt.setString(6, patient.getEmergency_relation());
            stmt.setString(7, patient.getAllergy());
            stmt.setInt(8, patient.getPatient_id());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	@Override
	public void deletePatient(int patient_id) {
		// TODO Auto-generated method stub
		try (Connection con = DBConnection.getConnection();
				 PreparedStatement stmt = con.prepareStatement(DELETE_PATIENT)) {
				
			stmt.setInt(1, patient_id);
				stmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}





