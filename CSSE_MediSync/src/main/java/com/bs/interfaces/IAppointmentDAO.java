package com.bs.interfaces;

import com.bs.model.Appointment;
import java.sql.SQLException;
import java.util.List;

public interface IAppointmentDAO {
    
    // Retrieve all appointment details
    List<Appointment> getAppointmentDetails() throws SQLException;
    
    // Create a new appointment
    boolean createAppointment(Appointment appointment) throws SQLException;
    
    // Edit or update an existing appointment by ID
    boolean updateAppointment(Appointment appointment) throws SQLException;
    
    // Delete an appointment by ID
    boolean deleteAppointment(int appointmentId) throws SQLException;
    
    boolean isAppointmentExists(Appointment appointment) throws SQLException;
}
