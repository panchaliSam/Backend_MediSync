package com.bs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bs.interfaces.IDoctorAvailabilityDAO;
import com.bs.model.DoctorAvailability;
import com.bs.utility.DBConnection;

public class DoctorAvailabilityDAO implements IDoctorAvailabilityDAO {
    // SQL Queries for Doctor Availability operations
    private static final String SELECT_AVAILABLE_SLOTS = "SELECT d.doctor_name, " +
            "da.available_date, h.hospital_name, da.start_time, da.end_time, " +
            "COUNT(a.appointment_id) AS allocated_patients " +
            "FROM doctor_availability da " +
            "JOIN doctor d ON da.doctor_id = d.doctor_id " +
            "JOIN hospital h ON da.hospital_id = h.hospital_id " +
            "LEFT JOIN appointment a ON da.doctor_id = a.doctor_id " +
            "AND da.available_date = a.appointment_date " +
            "AND da.hospital_id = a.hospital_id " +
            "WHERE d.doctor_name = ? AND da.is_available = TRUE " +
            "GROUP BY d.doctor_name, da.available_date, h.hospital_name, da.start_time, da.end_time " +
            "ORDER BY da.available_date, da.start_time";

    // Retrieve available slots for a specific doctor
    @Override
    public List<DoctorAvailability> getAvailableSlotsByDoctor(String doctorName) {
        List<DoctorAvailability> availabilityList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_AVAILABLE_SLOTS)) {
             
            stmt.setString(1, doctorName); // Set the doctor name parameter
            ResultSet rs = stmt.executeQuery(); // Execute the query

            while (rs.next()) {
                // Create a new DoctorAvailability object for each row in the result set
                DoctorAvailability availability = new DoctorAvailability();
                availability.setDoctorName(rs.getString("doctor_name"));
                availability.setAvailableDate(rs.getDate("available_date").toLocalDate());
                availability.setHospitalName(rs.getString("hospital_name"));
                availability.setStartTime(rs.getTime("start_time").toLocalTime());
                availability.setEndTime(rs.getTime("end_time").toLocalTime());
                availability.setAllocatedPatients(rs.getInt("allocated_patients"));

                // Add the availability object to the list
                availabilityList.add(availability);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately (e.g., log the error)
            // You might want to throw a custom exception or return an empty list based on your error handling strategy
        }

        return availabilityList; // Return the list of available slots
    }
}
