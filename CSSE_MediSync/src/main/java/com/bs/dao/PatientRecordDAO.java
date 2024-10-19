package com.bs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bs.interfaces.IPatientRecordDAO;
import com.bs.model.PatientRecord;
import com.bs.utility.DBConnection;

public class PatientRecordDAO implements IPatientRecordDAO{
    // SQL Queries
	String SELECT_ALL_PATIENT_RECORDS = "SELECT pr.record_id, p.patient_name, h.hospital_name, d.doctor_name, a.appointment_id, pr.diagnosis, pr.medicines, pr.lab_test_report_link " +
			"FROM patient_record pr " +
	        "JOIN patient p ON pr.patient_id = p.patient_id " +
	        "JOIN hospital h ON pr.hospital_id = h.hospital_id " +
	        "JOIN doctor d ON pr.doctor_id = d.doctor_id " + 
	        "JOIN appointment a ON pr.appointment_id = a.appointment_id";
	
	String SELECT_PATIENT_RECORD = "SELECT pr.record_id, p.patient_name, h.hospital_name, d.doctor_name, a.appointment_id, pr.diagnosis, pr.medicines, pr.lab_test_report_link " +
	        "FROM patient_record pr " +
	        "JOIN patient p ON pr.patient_id = p.patient_id " +
	        "JOIN hospital h ON pr.hospital_id = h.hospital_id " +
	        "JOIN doctor d ON pr.doctor_id = d.doctor_id " + 
	        "JOIN appointment a ON pr.appointment_id = a.appointment_id " + 
	        "WHERE pr.record_id = ?";

	String INSERT_PATIENT_RECORD = "INSERT INTO patient_record (patient_id, hospital_id, doctor_id, appointment_id, diagnosis, medicines, lab_test_report_link) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    String UPDATE_PATIENT_RECORD = "UPDATE patient_record SET patient_id = ?, hospital_id = ?, doctor_id = ?, appointment_id = ?, diagnosis = ?, medicines = ?, lab_test_report_link = ? " +
            "WHERE record_id = ?";

    String DELETE_PATIENT_RECORD = "DELETE FROM patient_record WHERE record_id = ?";

    // Select All Patient Records
    public List<PatientRecord> selectAllPatientRecords() {
        List<PatientRecord> patientRecords = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ALL_PATIENT_RECORDS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int record_id = rs.getInt("record_id");
                String patient_name = rs.getString("patient_name");
                String hospital_name = rs.getString("hospital_name");
                String doctor_name = rs.getString("doctor_name");
                int appointment_id = rs.getInt("appointment_id");
                String diagnosis = rs.getString("diagnosis");
                String medicines = rs.getString("medicines");
                String lab_test_report_link = rs.getString("lab_test_report_link");

                // Create PatientRecord with IDs but use names for display
                PatientRecord patientRecord = new PatientRecord(record_id, patient_name, hospital_name, doctor_name, appointment_id, diagnosis, medicines, lab_test_report_link);
                patientRecords.add(patientRecord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patientRecords;
    }

    // Select a Patient Record by ID
    public PatientRecord selectPatientRecord(int recordId) {
        PatientRecord patientRecord = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_PATIENT_RECORD)) {

            stmt.setInt(1, recordId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("record_id");
                    String patient_name = rs.getString("patient_name");
                    String hospital_name = rs.getString("hospital_name");
                    String doctor_name = rs.getString("doctor_name");
                    int appointment_id = rs.getInt("appointment_id");
                    String diagnosis = rs.getString("diagnosis");
                    String medicines = rs.getString("medicines");
                    String lab_test_report_link = rs.getString("lab_test_report_link");

                    patientRecord = new PatientRecord(id, patient_name, hospital_name, doctor_name, appointment_id, diagnosis, medicines, lab_test_report_link);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patientRecord;
    }

    // Insert a New Patient Record
    public int insertPatientRecord(PatientRecord patientRecord) {
        // Validate existence of names and map to IDs
        int patientId = getPatientIdByName(patientRecord.getPatient_name());
        int hospitalId = getHospitalIdByName(patientRecord.getHospital_name());
        int doctorId = getDoctorIdByName(patientRecord.getDoctor_name());

        if (patientId == -1) throw new IllegalArgumentException("Patient does not exist.");
        if (hospitalId == -1) throw new IllegalArgumentException("Hospital does not exist.");
        if (doctorId == -1) throw new IllegalArgumentException("Doctor does not exist.");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_PATIENT_RECORD)) {

            stmt.setInt(1, patientId);
            stmt.setInt(2, hospitalId);
            stmt.setInt(3, doctorId);
            stmt.setInt(4, patientRecord.getAppointment_id());
            stmt.setString(5, patientRecord.getDiagnosis());
            stmt.setString(6, patientRecord.getMedicines());
            stmt.setString(7, patientRecord.getLab_test_report_link());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Update an Existing Patient Record
    public void updatePatientRecord(PatientRecord patientRecord) {
        // Validate existence of names and map to IDs
        int patientId = getPatientIdByName(patientRecord.getPatient_name());
        int hospitalId = getHospitalIdByName(patientRecord.getHospital_name());
        int doctorId = getDoctorIdByName(patientRecord.getDoctor_name());

        if (patientId == -1) throw new IllegalArgumentException("Patient does not exist.");
        if (hospitalId == -1) throw new IllegalArgumentException("Hospital does not exist.");
        if (doctorId == -1) throw new IllegalArgumentException("Doctor does not exist.");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_PATIENT_RECORD)) {

            stmt.setInt(1, patientId);
            stmt.setInt(2, hospitalId);
            stmt.setInt(3, doctorId);
            stmt.setInt(4, patientRecord.getAppointment_id());
            stmt.setString(5, patientRecord.getDiagnosis());
            stmt.setString(6, patientRecord.getMedicines());
            stmt.setString(7, patientRecord.getLab_test_report_link());
            stmt.setInt(8, patientRecord.getRecord_id());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete a Patient Record
    public void deletePatientRecord(int recordId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_PATIENT_RECORD)) {
            stmt.setInt(1, recordId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper Methods to Get IDs from Names
    private int getPatientIdByName(String patientName) {
        String query = "SELECT patient_id FROM patient WHERE patient_name = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, patientName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("patient_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Not found
    }

    private int getHospitalIdByName(String hospitalName) {
        String query = "SELECT hospital_id FROM hospital WHERE hospital_name = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, hospitalName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("hospital_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Not found
    }

    private int getDoctorIdByName(String doctorName) {
        String query = "SELECT doctor_id FROM doctor WHERE doctor_name = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, doctorName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("doctor_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Not found
    }
}
