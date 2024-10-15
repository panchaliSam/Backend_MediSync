package com.bs.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.bs.dao.AppointmentDAO;
import com.bs.interfaces.IAppointmentDAO;
import com.bs.model.Appointment;
import com.bs.model.LocalDateSerializer;
import com.bs.model.LocalTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/appointments")
public class AppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IAppointmentDAO appointmentDAO; // Use final for better clarity
    private final Gson gson; // Declare Gson instance

    public AppointmentServlet() {
        super();
        this.appointmentDAO = new AppointmentDAO(); // Initialize the DAO
        // Initialize Gson with custom serializer for LocalDate
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeSerializer())
                .create();
    } 

    private void addCorsHeaders(HttpServletResponse response) {
        // Allow requests from your frontend origin (adjust as necessary)
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addCorsHeaders(response);
        response.setContentType("application/json");

        try {
            // Fetch the list of appointment details
            List<Appointment> appointments = appointmentDAO.getAppointmentDetails();

            // Convert the list to JSON and send as response
            String jsonResponse = gson.toJson(appointments);
            response.getWriter().write(jsonResponse);

        } catch (SQLException e) {
            // Log the error for debugging
            e.printStackTrace();

            // If an error occurs, respond with an error message and status code
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error fetching appointment details: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            // Handle unexpected errors
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"An unexpected error occurred: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
