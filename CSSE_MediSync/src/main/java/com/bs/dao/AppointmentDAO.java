package com.bs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.bs.interfaces.IAppointmentDAO;
import com.bs.model.Appointment;
import com.bs.utility.DBConnection;

public class AppointmentDAO implements IAppointmentDAO {
    // SQL Queries for Appointment operations
    private static final String SELECT_ALL_APPOINTMENTS = "SELECT " +
                     "   a.appointment_id, " +
                     "   h.hospital_name, " +
                     "   d.doctor_name, " +
                     "   d.specialization, " +
                     "   a.appointment_date, " +
                     "   a.appointment_time, " +
                     "   p.patient_name " + // Removed payment_id from the selection
                     "FROM appointment a " +
                     "INNER JOIN hospital h ON h.hospital_id = a.hospital_id " +
                     "INNER JOIN doctor d ON d.doctor_id = a.doctor_id " +
                     "INNER JOIN patient p ON p.patient_id = a.patient_id";

    private static final String INSERT_APPOINTMENT = "INSERT INTO appointment " +
                     "(hospital_id, doctor_id, patient_id, payment_id, appointment_date, appointment_time) " +
                     "VALUES (?, ?, ?, NULL, ?, ?)"; // Set payment_id to NULL

    private static final String UPDATE_APPOINTMENT = "UPDATE appointment SET " +
                     "hospital_id = ?, doctor_id = ?, patient_id = ?, payment_id = NULL, " + // Set payment_id to NULL
                     "appointment_date = ?, appointment_time = ? WHERE appointment_id = ?";

    private static final String DELETE_APPOINTMENT = "DELETE FROM appointment WHERE appointment_id = ?";

    // Retrieve all appointment details
    @Override
    public List<Appointment> getAppointmentDetails() throws SQLException {
        List<Appointment> appointmentDetailsList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ALL_APPOINTMENTS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Fetching data from the ResultSet
                int appointmentId = rs.getInt("appointment_id");
                String hospitalName = rs.getString("hospital_name");
                String doctorName = rs.getString("doctor_name");
                String specialization = rs.getString("specialization");
                LocalDate appointmentDate = rs.getDate("appointment_date").toLocalDate(); // Converts SQL Date to LocalDate
                LocalTime appointmentTime = rs.getTime("appointment_time").toLocalTime(); // Converts SQL Time to LocalTime
                String patientName = rs.getString("patient_name");

                // Creating an Appointment object with the fetched data
                Appointment details = new Appointment(
                    appointmentId, hospitalName, doctorName, specialization,
                    appointmentDate, appointmentTime, patientName // payment_id is no longer included
                );

                // Adding the Appointment object to the list
                appointmentDetailsList.add(details);
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving appointment details: " + e.getMessage(), e);
        }

        return appointmentDetailsList;
    }

    // Insert an appointment by retrieving relevant IDs
    @Override
    public boolean createAppointment(Appointment appointment) throws SQLException {
        if (isAppointmentExists(appointment)) {
            throw new SQLException("An appointment already exists for this patient with the same doctor on this date.");
        }
        return executeAppointmentOperation(appointment, INSERT_APPOINTMENT, 0);
    }

    // Update an existing appointment by ID
    @Override
    public boolean updateAppointment(Appointment appointment) throws SQLException {
        return executeAppointmentOperation(appointment, UPDATE_APPOINTMENT, appointment.getAppointmentId());
    }

    // Delete an appointment by ID
    @Override
    public boolean deleteAppointment(int appointmentId) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_APPOINTMENT)) {

            stmt.setInt(1, appointmentId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new SQLException("Error deleting appointment: " + e.getMessage(), e);
        }
    }

    private boolean executeAppointmentOperation(Appointment appointment, String sql, int appointmentId) throws SQLException {
        String selectHospitalIdQuery = "SELECT hospital_id FROM hospital WHERE hospital_name = ?";
        String selectDoctorIdQuery = "SELECT doctor_id FROM doctor WHERE doctor_name = ?";
        String selectPatientIdQuery = "SELECT patient_id FROM patient WHERE patient_name = ?";

        try (Connection con = DBConnection.getConnection()) {
            // Retrieve Hospital ID
            int hospitalId;
            try (PreparedStatement stmt = con.prepareStatement(selectHospitalIdQuery)) {
                stmt.setString(1, appointment.getHospitalName());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        hospitalId = rs.getInt("hospital_id");
                    } else {
                        throw new SQLException("Hospital not found: " + appointment.getHospitalName());
                    }
                }
            }

            // Retrieve Doctor ID
            int doctorId;
            try (PreparedStatement stmt = con.prepareStatement(selectDoctorIdQuery)) {
                stmt.setString(1, appointment.getDoctorName());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        doctorId = rs.getInt("doctor_id");
                    } else {
                        throw new SQLException("Doctor not found: " + appointment.getDoctorName());
                    }
                }
            }

            // Retrieve Patient ID
            int patientId;
            try (PreparedStatement stmt = con.prepareStatement(selectPatientIdQuery)) {
                stmt.setString(1, appointment.getPatientName());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        patientId = rs.getInt("patient_id");
                    } else {
                        throw new SQLException("Patient not found: " + appointment.getPatientName());
                    }
                }
            }

            // Now that we have the IDs, insert or update the appointment
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, hospitalId); // Hospital ID
                stmt.setInt(2, doctorId);   // Doctor ID
                stmt.setInt(3, patientId);  // Patient ID
                stmt.setDate(4, Date.valueOf(appointment.getAppointmentDate()));  // Appointment date
                stmt.setTime(5, Time.valueOf(appointment.getAppointmentTime()));  // Appointment time

                // If this is an update, we need to set the appointment ID as the 6th parameter
                if (appointmentId > 0) {
                    stmt.setInt(6, appointmentId);  // Set appointment ID
                }

                return stmt.executeUpdate() > 0;  // Execute the update/insert
            }

        } catch (SQLException e) {
            throw new SQLException("Error processing appointment: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean isAppointmentExists(Appointment appointment) throws SQLException {
        String query = "SELECT COUNT(*) FROM appointment WHERE appointment_date = ? " +
                       "AND patient_id = (SELECT patient_id FROM patient WHERE patient_name = ?) " +
                       "AND doctor_id = (SELECT doctor_id FROM doctor WHERE doctor_name = ?) " +
                       "AND hospital_id = (SELECT hospital_id FROM hospital WHERE hospital_name = ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(appointment.getAppointmentDate()));
            stmt.setString(2, appointment.getPatientName());
            stmt.setString(3, appointment.getDoctorName());
            stmt.setString(4, appointment.getHospitalName());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if any existing appointment is found
            }
        } catch (SQLException e) {
            throw new SQLException("Error checking existing appointment: " + e.getMessage(), e);
        }
        return false;
    }

}
