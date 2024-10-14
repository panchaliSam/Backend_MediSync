package com.bs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bs.interfaces.IDoctorDAO;
import com.bs.model.Doctor;
import com.bs.utility.DBConnection;

public class DoctorDAO implements IDoctorDAO {
    // SQL Queries for Doctor operations
    String SELECT_DOCTOR_BY_ID = "SELECT * FROM doctor WHERE doctor_id = ?";
    String SELECT_ALL_DOCTORS = "SELECT * FROM doctor";
    String INSERT_DOCTOR = "INSERT INTO doctor (doctor_name, specialization, contact_no, hospital_id, doctor_charge) VALUES (?, ?, ?, ?, ?)";
    String UPDATE_DOCTOR = "UPDATE doctor SET doctor_name=?, specialization=?, contact_no=?, hospital_id=?, doctor_charge=? WHERE doctor_id = ?";
    String DELETE_DOCTOR = "DELETE FROM doctor WHERE doctor_id = ?";

    @Override
    public List<Doctor> selectAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ALL_DOCTORS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int doctor_id = rs.getInt("doctor_id");
                String doctor_name = rs.getString("doctor_name");
                String specialization = rs.getString("specialization");
                String contact_no = rs.getString("contact_no");
                int hospital_id = rs.getInt("hospital_id");
                double doctor_charge = rs.getDouble("doctor_charge");

                Doctor doctor = new Doctor(doctor_id, doctor_name, specialization, contact_no, hospital_id, doctor_charge);
                doctors.add(doctor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doctors;
    }

    @Override
    public Doctor selectDoctor(int doctor_id) {
        Doctor doctor = new Doctor();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_DOCTOR_BY_ID)) {

            stmt.setInt(1, doctor_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String doctor_name = rs.getString("doctor_name");
                    String specialization = rs.getString("specialization");
                    String contact_no = rs.getString("contact_no");
                    int hospital_id = rs.getInt("hospital_id");
                    double doctor_charge = rs.getDouble("doctor_charge");

                    doctor = new Doctor(doctor_id, doctor_name, specialization, contact_no, hospital_id, doctor_charge);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doctor;
    }
    
    //method to check if a hospital exists
    public boolean doesHospitalExist(int hospitalId) {
        String query = "SELECT COUNT(*) FROM hospital WHERE hospital_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, hospitalId);
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

    @Override
    public void insertDoctor(Doctor doctor) {
    	if (!doesHospitalExist(doctor.getHospital_id())) {
            throw new IllegalArgumentException("Hospital ID does not exist: " + doctor.getHospital_id());
        }
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_DOCTOR)) {

            stmt.setString(1, doctor.getDoctor_name());
            stmt.setString(2, doctor.getSpecialization());
            stmt.setString(3, doctor.getContact_no());
            stmt.setInt(4, doctor.getHospital_id()); 
            stmt.setDouble(5, doctor.getDoctor_charge());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDoctor(Doctor doctor) {
    	// Check if the hospital_id exists before updating
        if (!doesHospitalExist(doctor.getHospital_id())) {
            throw new IllegalArgumentException("Hospital ID does not exist: " + doctor.getHospital_id());
        }

        String UPDATE_DOCTOR = "UPDATE doctor SET doctor_name=?, specialization=?, contact_no=?, hospital_id=?, doctor_charge=? WHERE doctor_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_DOCTOR)) {

            stmt.setString(1, doctor.getDoctor_name());
            stmt.setString(2, doctor.getSpecialization());
            stmt.setString(3, doctor.getContact_no());
            stmt.setInt(4, doctor.getHospital_id()); // Setting the hospital_id
            stmt.setDouble(5, doctor.getDoctor_charge());
            stmt.setInt(6, doctor.getDoctor_id()); // Setting the doctor_id to identify the record

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDoctor(int doctor_id) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_DOCTOR)) {

            stmt.setInt(1, doctor_id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
