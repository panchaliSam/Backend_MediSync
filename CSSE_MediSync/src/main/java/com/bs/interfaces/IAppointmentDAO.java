package com.bs.interfaces;

import com.bs.model.Appointment;
import java.sql.SQLException;
import java.util.List;

public interface IAppointmentDAO {
    List<Appointment> getAppointmentDetails() throws SQLException;
}
