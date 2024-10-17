package com.bs.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.bs.dao.DoctorAvailabilityDAO;
import com.bs.interfaces.IDoctorAvailabilityDAO;
import com.bs.model.DoctorAvailability;
import com.bs.model.LocalDateDeserializer;
import com.bs.model.LocalDateSerializer;
import com.bs.model.LocalTimeDeserializer;
import com.bs.model.LocalTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.bs.utility.CorsUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/doctor-availability")
public class DoctorAvailabilityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IDoctorAvailabilityDAO doctorAvailabilityDAO; 
    private final Gson gson; 

    public DoctorAvailabilityServlet() {
        super();
        this.doctorAvailabilityDAO = new DoctorAvailabilityDAO(); 
        GsonBuilder gsonBuilder = new GsonBuilder();
        // Register serializers and deserializers for LocalDate and LocalTime
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeSerializer());
        this.gson = gsonBuilder.create(); // Create the Gson instance
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CorsUtil.addCorsHeaders(response);
        response.setContentType("application/json");

        String doctorName = request.getParameter("doctorName"); // Get doctor name from request parameters

        if (doctorName == null || doctorName.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Doctor name is required.\"}");
            return;
        }

        System.out.println("Received doctor name: " + doctorName); // Log received doctor name

        try {
            List<DoctorAvailability> availabilityList = doctorAvailabilityDAO.getAvailableSlotsByDoctor(doctorName);
            String jsonResponse = gson.toJson(availabilityList);
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace(); // Log unexpected exceptions for debugging
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"An unexpected error occurred: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CorsUtil.addCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
