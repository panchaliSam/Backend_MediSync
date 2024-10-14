package com.bs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bs.interfaces.IPatientRecordDAO;
import com.bs.model.PatientRecord;
import com.bs.utility.DBConnection;

public class PatientRecordDAO implements IPatientRecordDAO {
    
    // SQL Queries for PatientRecord operations
    String INSERT_PATIENT_RECORD = "INSERT INTO patient_record (patient_id, hospital_id, doctor_id, appointment_id, diagnosis, medicines, lab_test_report_link) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String SELECT_ALL_PATIENT_RECORDS = "SELECT * FROM patient_record";
    String SELECT_PATIENT_RECORD = "SELECT * FROM patient_record WHERE record_id = ?";
    String UPDATE_PATIENT_RECORD = "UPDATE patient_record SET patient_id = ?, hospital_id = ?, doctor_id = ?, appointment_id = ?, diagnosis = ?, medicines = ?, lab_test_report_link = ? WHERE record_id = ?";
    String DELETE_PATIENT_RECORD = "DELETE FROM patient_record WHERE record_id = ?";
    
    // Method to check if a patient exists
    public boolean doesPatientExist(int patient_id) {
        String query = "SELECT COUNT(*) FROM patient WHERE patient_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, patient_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if patient exists
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Patient doesn't exist
    }

    // Method to check if a hospital exists
    public boolean doesHospitalExist(int hospital_id) {
        String query = "SELECT COUNT(*) FROM hospital WHERE hospital_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, hospital_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if hospital exists
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Hospital doesn't exist
    }

    // Method to check if a doctor exists
    public boolean doesDoctorExist(int doctor_id) {
        String query = "SELECT COUNT(*) FROM doctor WHERE doctor_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, doctor_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if doctor exists
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Doctor doesn't exist
    }
    
 // Method to check if a doctor exists
    public boolean doesAppointmentExist(int appointment_id) {
        String query = "SELECT COUNT(*) FROM appointment WHERE appointment_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, appointment_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Returns true if appointment exists
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Appointment doesn't exist
    }

    @Override
    public void insertPatientRecord(PatientRecord patientRecord) {
        if (!doesPatientExist(patientRecord.getPatient_id())) {
            throw new IllegalArgumentException("Patient ID does not exist: " + patientRecord.getPatient_id());
        }
        if (!doesHospitalExist(patientRecord.getHospital_id())) {
            throw new IllegalArgumentException("Hospital ID does not exist: " + patientRecord.getHospital_id());
        }
        if (!doesDoctorExist(patientRecord.getDoctor_id())) {
            throw new IllegalArgumentException("Doctor ID does not exist: " + patientRecord.getDoctor_id());
        }
        if (!doesDoctorExist(patientRecord.getAppointment_id())) {
            throw new IllegalArgumentException("Appointment ID does not exist: " + patientRecord.getAppointment_id());
        }
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_PATIENT_RECORD)) {

            stmt.setInt(1, patientRecord.getPatient_id());
            stmt.setInt(2, patientRecord.getHospital_id());
            stmt.setInt(3, patientRecord.getDoctor_id());
            stmt.setInt(4, patientRecord.getAppointment_id());
            stmt.setString(5, patientRecord.getDiagnosis());
            stmt.setString(6, patientRecord.getMedicines());
            stmt.setString(7, patientRecord.getLab_test_report_link());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PatientRecord> selectAllPatientRecords() {
        List<PatientRecord> patientRecords = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ALL_PATIENT_RECORDS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int record_id = rs.getInt("record_id");
                int patient_id = rs.getInt("patient_id");
                int hospital_id = rs.getInt("hospital_id");
                int doctor_id = rs.getInt("doctor_id");
                int appointment_id = rs.getInt("appointment_id");
                String diagnosis = rs.getString("diagnosis");
                String medicines = rs.getString("medicines");
                String lab_test_report_link = rs.getString("lab_test_report_link");

                PatientRecord patientRecord = new PatientRecord(record_id, patient_id, hospital_id, doctor_id, appointment_id, diagnosis, medicines, lab_test_report_link);
                patientRecords.add(patientRecord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patientRecords;
    }

    @Override
    public PatientRecord selectPatientRecord(int record_id) {
        PatientRecord patientRecord = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_PATIENT_RECORD)) {

            stmt.setInt(1, record_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int patient_id = rs.getInt("patient_id");
                    int hospital_id = rs.getInt("hospital_id");
                    int doctor_id = rs.getInt("doctor_id");
                    int appointment_id = rs.getInt("appointment_id");
                    String diagnosis = rs.getString("diagnosis");
                    String medicines = rs.getString("medicines");
                    String lab_test_report_link = rs.getString("lab_test_report_link");

                    patientRecord = new PatientRecord(record_id, patient_id, hospital_id, doctor_id, appointment_id, diagnosis, medicines, lab_test_report_link);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patientRecord;
    }

    @Override
    public void updatePatientRecord(PatientRecord patientRecord) {
        if (!doesPatientExist(patientRecord.getPatient_id())) {
            throw new IllegalArgumentException("Patient ID does not exist: " + patientRecord.getPatient_id());
        }
        if (!doesHospitalExist(patientRecord.getHospital_id())) {
            throw new IllegalArgumentException("Hospital ID does not exist: " + patientRecord.getHospital_id());
        }
        if (!doesDoctorExist(patientRecord.getDoctor_id())) {
        	throw new IllegalArgumentException("Doctor ID does not exist: " + patientRecord.getDoctor_id());
        }
        if (!doesDoctorExist(patientRecord.getAppointment_id())) {
            throw new IllegalArgumentException("Appoinment ID does not exist: " + patientRecord.getAppointment_id());
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_PATIENT_RECORD)) {

            stmt.setInt(1, patientRecord.getPatient_id());
            stmt.setInt(2, patientRecord.getHospital_id());
            stmt.setInt(3, patientRecord.getDoctor_id());
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

    @Override
    public void deletePatientRecord(int record_id) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_PATIENT_RECORD)) {

            stmt.setInt(1, record_id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
