package com.bs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bs.interfaces.IDoctorDAO;
import com.bs.model.Doctor;
import com.bs.utility.DBConnection;

public class DoctorDAO implements IDoctorDAO {
    // SQL Queries for Doctor operations
    String SELECT_DOCTOR_BY_ID = "SELECT d.doctor_id, d.doctor_name, d.specialization, d.contact_no, h.hospital_name, d.doctor_charge " +
            "FROM doctor d " +
            "INNER JOIN hospital h ON d.hospital_id = h.hospital_id " +
            "WHERE d.doctor_id = ?";

    String SELECT_ALL_DOCTORS = "SELECT d.doctor_id, d.doctor_name, d.specialization, d.contact_no, h.hospital_name, d.doctor_charge " +
            "FROM doctor d " +
            "INNER JOIN hospital h ON d.hospital_id = h.hospital_id";

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
                String hospital_name = rs.getString("hospital_name"); 
                double doctor_charge = rs.getDouble("doctor_charge");

                Doctor doctor = new Doctor(doctor_id, doctor_name, specialization, contact_no, hospital_name, doctor_charge);
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
                    String hospital_name = rs.getString("hospital_name"); 
                    double doctor_charge = rs.getDouble("doctor_charge");

                    doctor = new Doctor(doctor_id, doctor_name, specialization, contact_no, hospital_name, doctor_charge);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doctor;
    }


    // Method to check if a hospital exists
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

    // Method to get hospital ID by hospital name
    public int getHospitalIdByName(String hospitalName) {
        int hospitalId = -1;
        String query = "SELECT hospital_id FROM hospital WHERE hospital_name = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, hospitalName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    hospitalId = rs.getInt("hospital_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hospitalId;
    }

    @Override
    public void insertDoctor(Doctor doctor) {
        // Get the hospital ID based on the provided hospital name
        int hospitalId = getHospitalIdByName(doctor.getHospital_name());
        if (hospitalId == -1) {
            throw new IllegalArgumentException("Hospital name does not exist: " + doctor.getHospital_name());
        }

        // Check if the doctor with the same name already exists
        if (isDoctorNameExists(doctor.getDoctor_name())) {
            throw new IllegalArgumentException("Doctor with the name " + doctor.getDoctor_name() + " already exists.");
        }

        // Insert the doctor into the database
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_DOCTOR)) {

            stmt.setString(1, doctor.getDoctor_name());
            stmt.setString(2, doctor.getSpecialization());
            stmt.setString(3, doctor.getContact_no());
            stmt.setInt(4, hospitalId);
            stmt.setDouble(5, doctor.getDoctor_charge());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to insert doctor: " + e.getMessage());
        }
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        // Validate the hospital name first
        int hospitalId = getHospitalIdByName(doctor.getHospital_name());
        if (hospitalId == -1) {
            throw new IllegalArgumentException("Hospital name does not exist: " + doctor.getHospital_name());
        }

        // Check if the doctor exists
        if (!doctorExists(doctor.getDoctor_id())) {
            throw new IllegalArgumentException("Doctor with ID " + doctor.getDoctor_id() + " does not exist.");
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_DOCTOR)) {

            stmt.setString(1, doctor.getDoctor_name());
            stmt.setString(2, doctor.getSpecialization());
            stmt.setString(3, doctor.getContact_no());
            stmt.setInt(4, hospitalId);
            stmt.setDouble(5, doctor.getDoctor_charge());
            stmt.setInt(6, doctor.getDoctor_id());

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
    
 // Helper method to check if a doctor name already exists
    private boolean isDoctorNameExists(String doctorName) {
        String query = "SELECT COUNT(*) FROM doctors WHERE doctor_name = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, doctorName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if a doctor with the given name exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Returns false if no doctor with the name exists
    }
    
 // Method to check if a doctor exists
    private boolean doctorExists(int doctorId) {
        String query = "SELECT COUNT(*) FROM doctor WHERE doctor_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if count is greater than 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if any exception occurs or doctor doesn't exist
    }
}
