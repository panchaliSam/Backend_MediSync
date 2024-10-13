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
	String SELECT_PATIENT_BY_ID = "SELECT * FROM patient WHERE patientID = ?";
	
	String SELECT_ALL_PATIENTS = "SELECT * FROM patient";
	
	String INSERT_PATIENT = "INSERT INTO patient (patientName, patientAge, patientEmail, medicalHistory, allergies, emergencyContact, relation) VALUES (?, ?, ?, ?, ?, ?, ?)";

	String UPDATE_PATIENT = "UPDATE patient SET patientName=?, patientAge=?, patientEmail=?, medicalHistory=?, allergies=?, emergencyContact=?, relation=? WHERE patientID = ?";
	
	String DELETE_PATIENT = "DELETE FROM patient WHERE patientID = ?";
	
	@Override
	public List<Patient> selectAllPatients() {
		// TODO Auto-generated method stub
		List<Patient> patients = new ArrayList<>();
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_ALL_PATIENTS);
			 ResultSet rs = stmt.executeQuery()) {
			
			while (rs.next()) {
				int patientID = rs.getInt("patientID");
				String patientName = rs.getString("patientName");
				int patientAge = rs.getInt("patientAge");
				String patientEmail = rs.getString("patientEmail");
				String medicalHistory = rs.getString("medicalHistory");
				String allergies = rs.getString("allergies");
				String emergencyContact = rs.getString("emergencyContact");
				String relation = rs.getString("relation");
				
				Patient patient = new Patient(patientID, patientName, patientAge, patientEmail, medicalHistory, allergies, emergencyContact, relation);
				patients.add(patient);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patients;
	}
	@Override
	public Patient selectPatient(int patientID) {
		// TODO Auto-generated method stub
		Patient patient = new Patient();
		try (Connection con = DBConnection.getConnection();
			 PreparedStatement stmt = con.prepareStatement(SELECT_PATIENT_BY_ID)) {
			
			stmt.setInt(1, patientID);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					String patientName = rs.getString("patientName");
					int patientAge = rs.getInt("patientAge");
					String patientEmail = rs.getString("patientEmail");
					String medicalHistory = rs.getString("medicalHistory");
					String allergies = rs.getString("allergies");
					String emergencyContact = rs.getString("emergencyContact");
					String relation = rs.getString("relation");
					
					patient = new Patient(patientID, patientName, patientAge, patientEmail, medicalHistory, allergies, emergencyContact, relation);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patient;
	}
	@Override
	public void insertPatient(Patient patient) {
		// TODO Auto-generated method stub
		try (Connection con = DBConnection.getConnection();
				 PreparedStatement stmt = con.prepareStatement(INSERT_PATIENT)) {
				
				stmt.setString(1, patient.getPatientName());
				stmt.setInt(2, patient.getPatientAge());
				stmt.setString(3, patient.getPatientEmail());
				stmt.setString(4, patient.getMedicalHistory());
				stmt.setString(5, patient.getAllergies());
				stmt.setString(6, patient.getEmergencyContact());
				stmt.setString(7, patient.getRelation());
				
				stmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	@Override
	public void updatePatient(Patient patient) {
		// TODO Auto-generated method stub
		try (Connection con = DBConnection.getConnection();
				 PreparedStatement stmt = con.prepareStatement(UPDATE_PATIENT)) {
				
				stmt.setString(1, patient.getPatientName());
				stmt.setInt(2, patient.getPatientAge());
				stmt.setString(3, patient.getPatientEmail());
				stmt.setString(4, patient.getMedicalHistory());
				stmt.setString(5, patient.getAllergies());
				stmt.setString(6, patient.getEmergencyContact());
				stmt.setString(7, patient.getRelation());
				stmt.setInt(8, patient.getPatientID());
				
				stmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	@Override
	public void deletePatient(int patientID) {
		// TODO Auto-generated method stub
		try (Connection con = DBConnection.getConnection();
				 PreparedStatement stmt = con.prepareStatement(DELETE_PATIENT)) {
				
				stmt.setInt(1, patientID);
				stmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}





