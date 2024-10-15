package com.bs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                     "   p.patient_name, " +
                     "   pay.amount " +
                     "FROM appointment a " +
                     "INNER JOIN hospital h ON h.hospital_id = a.hospital_id " +
                     "INNER JOIN doctor d ON d.doctor_id = a.doctor_id " +
                     "INNER JOIN patient p ON p.patient_id = a.patient_id " +
                     "INNER JOIN payment pay ON pay.payment_id = a.payment_id";

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
                double paymentAmount = rs.getDouble("amount"); // Fetches the payment amount

                // Creating an Appointment object with the fetched data
                Appointment details = new Appointment(
                    appointmentId, hospitalName, doctorName, specialization,
                    appointmentDate, appointmentTime, patientName, paymentAmount
                );

                // Adding the Appointment object to the list
                appointmentDetailsList.add(details);
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving appointment details: " + e.getMessage(), e);
        }

        return appointmentDetailsList;
    }
}
