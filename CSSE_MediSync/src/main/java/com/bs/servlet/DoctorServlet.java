package com.bs.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.bs.model.Doctor;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.bs.interfaces.IDoctorDAO;
import com.bs.dao.DoctorDAO;
import com.bs.utility.CorsUtil;

/**
 * Servlet implementation class DoctorServlet
 */
@WebServlet("/doctors")
public class DoctorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    IDoctorDAO iDoctorDAO = new DoctorDAO();
    private Gson gson = new Gson(); 
    
    public DoctorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		CorsUtil.addCorsHeaders(response);
        response.setContentType("application/json");

        if (action == null) {
            List<Doctor> doctors = iDoctorDAO.selectAllDoctors();
            response.getWriter().write(gson.toJson(doctors));
        } else if (action.equals("edit")) {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Missing doctor ID\"}");
                return;
            }

            idParam = idParam.trim();

            try {
                int doctor_id = Integer.parseInt(idParam);
                Doctor doctor = iDoctorDAO.selectDoctor(doctor_id);
                if (doctor != null) {
                    response.getWriter().write(gson.toJson(doctor));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"Doctor not found\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Invalid doctor ID format\"}");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"An unexpected error occurred\"}");
            }
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CorsUtil.addCorsHeaders(response);
		response.setContentType("application/json");
        String action = request.getParameter("action");
        StringBuilder jsonBuffer = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        String jsonString = jsonBuffer.toString();
        Doctor doctor;

        try {
            doctor = gson.fromJson(jsonString, Doctor.class);
            if ("create".equals(action)) {
                iDoctorDAO.insertDoctor(doctor);
                response.getWriter().write("{\"message\": \"Doctor created successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Invalid action\"}");
            }
        } catch (JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid JSON format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"An error occurred: " + e.getMessage() + "\"}");
        }
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CorsUtil.addCorsHeaders(response);
		response.setContentType("application/json");

        String doctorIdParam = request.getParameter("doctor_id");
        if (doctorIdParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing doctor ID\"}");
            return;
        }

        int doctor_id;
        try {
            doctor_id = Integer.parseInt(doctorIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid Doctor ID format\"}");
            return;
        }

        StringBuilder jsonBuffer = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        String jsonString = jsonBuffer.toString();
        Doctor doctor;

        try {
            doctor = gson.fromJson(jsonString, Doctor.class);
            doctor.setDoctor_id(doctor_id); // Keep the original doctor ID

            if (doctor.getDoctor_name() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Missing required fields\"}");
                return;
            }

            iDoctorDAO.updateDoctor(doctor);
            response.getWriter().write("{\"message\": \"Doctor updated successfully\"}");
        } catch (JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid JSON format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"An error occurred: " + e.getMessage() + "\"}");
        }
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CorsUtil.addCorsHeaders(response);
		response.setContentType("application/json");
        String action = request.getParameter("action");
        String doctorIDStr = request.getParameter("id");

        if (action == null || doctorIDStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing parameters\"}");
            return;
        }

        try {
            if (!"delete".equals(action)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Invalid action\"}");
                return;
            }

            int doctor_id = Integer.parseInt(doctorIDStr.trim());
            iDoctorDAO.deleteDoctor(doctor_id);
            response.getWriter().write("{\"message\": \"Doctor deleted successfully\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid Doctor ID format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"An error occurred: " + e.getMessage() + "\"}");
        }
	}

}
