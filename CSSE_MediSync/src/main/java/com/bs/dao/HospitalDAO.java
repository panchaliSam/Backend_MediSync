package com.bs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bs.interfaces.IHospitalDAO;
import com.bs.model.Hospital;
import com.bs.utility.DBConnection;

public class HospitalDAO implements IHospitalDAO {

    // SQL Queries for Hospital operations
    String SELECT_HOSPITAL_BY_ID = "SELECT * FROM hospital WHERE hospital_id = ?";
    String SELECT_ALL_HOSPITALS = "SELECT * FROM hospital";
    String INSERT_HOSPITAL = "INSERT INTO hospital (hospital_name, hospital_charge) VALUES (?, ?)";
    String UPDATE_HOSPITAL = "UPDATE hospital SET hospital_name=?, hospital_charge=? WHERE hospital_id = ?";
    String DELETE_HOSPITAL = "DELETE FROM hospital WHERE hospital_id = ?";
    String CHECK_HOSPITAL_NAME_EXISTS = "SELECT COUNT(*) FROM hospital WHERE hospital_name = ? AND hospital_id != ?";

    // Helper method to check if hospital name is unique
    public boolean isHospitalNameUnique(String hospital_name, int hospital_id) throws Exception {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(CHECK_HOSPITAL_NAME_EXISTS)) {

            stmt.setString(1, hospital_name);
            stmt.setInt(2, hospital_id); // Ignore the current hospital's ID for updates
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0; // True if no hospital with this name exists
                }
            }
        }
        return false;
    }

    @Override
    public List<Hospital> selectAllHospitals() {
        List<Hospital> hospitals = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ALL_HOSPITALS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int hospital_id = rs.getInt("hospital_id");
                String hospital_name = rs.getString("hospital_name");
                double hospital_charge = rs.getDouble("hospital_charge");

                Hospital hospital = new Hospital(hospital_id, hospital_name, hospital_charge);
                hospitals.add(hospital);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hospitals;
    }

    @Override
    public Hospital selectHospital(int hospital_id) {
        Hospital hospital = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_HOSPITAL_BY_ID)) {

            stmt.setInt(1, hospital_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hospital_name = rs.getString("hospital_name");
                    double hospital_charge = rs.getDouble("hospital_charge");

                    hospital = new Hospital(hospital_id, hospital_name, hospital_charge);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hospital;
    }

    @Override
    public void insertHospital(Hospital hospital) {
        try {
            // Validate uniqueness of hospital name
            if (!isHospitalNameUnique(hospital.getHospital_name(), -1)) { // -1 means this is a new hospital
                throw new IllegalArgumentException("Hospital name must be unique");
            }

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement stmt = con.prepareStatement(INSERT_HOSPITAL)) {

                stmt.setString(1, hospital.getHospital_name());
                stmt.setDouble(2, hospital.getHospital_charge());

                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateHospital(Hospital hospital) {
        try {
            // Validate uniqueness of hospital name for update (excluding the current hospital)
            if (!isHospitalNameUnique(hospital.getHospital_name(), hospital.getHospital_id())) {
                throw new IllegalArgumentException("Hospital name must be unique");
            }

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement stmt = con.prepareStatement(UPDATE_HOSPITAL)) {

                stmt.setString(1, hospital.getHospital_name());
                stmt.setDouble(2, hospital.getHospital_charge());
                stmt.setInt(3, hospital.getHospital_id());

                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteHospital(int hospital_id) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_HOSPITAL)) {

            stmt.setInt(1, hospital_id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
