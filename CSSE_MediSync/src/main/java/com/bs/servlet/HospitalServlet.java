package com.bs.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.bs.model.Hospital;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.bs.dao.HospitalDAO;
import com.bs.interfaces.IHospitalDAO;
import com.bs.utility.CorsUtil;

/**
 * Servlet implementation class HospitalServlet
 */
@WebServlet("/hospitals")
public class HospitalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    IHospitalDAO iHospitalDAO = new HospitalDAO();
    private Gson gson = new Gson(); 
    
    public HospitalServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        CorsUtil.addCorsHeaders(response);
        response.setContentType("application/json");

        if (action == null) {
            List<Hospital> hospitals = iHospitalDAO.selectAllHospitals();
            response.getWriter().write(gson.toJson(hospitals));
        } else if (action.equals("edit")) {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Missing hospital ID\"}");
                return;
            }

            idParam = idParam.trim();

            try {
                int hospital_id = Integer.parseInt(idParam);
                Hospital hospital = iHospitalDAO.selectHospital(hospital_id);
                if (hospital != null) {
                    response.getWriter().write(gson.toJson(hospital));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"Hospital not found\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Invalid hospital ID format\"}");
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
        Hospital hospital;

        try {
            hospital = gson.fromJson(jsonString, Hospital.class);
            if ("create".equals(action)) {
                iHospitalDAO.insertHospital(hospital);
                response.getWriter().write("{\"message\": \"Hospital created successfully\"}");
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
     * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	CorsUtil.addCorsHeaders(response);
    	response.setContentType("application/json");

        String hospitalIdParam = request.getParameter("hospital_id");
        if (hospitalIdParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing hospital ID\"}");
            return;
        }

        int hospital_id;
        try {
            hospital_id = Integer.parseInt(hospitalIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid hospital ID format\"}");
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
        Hospital hospital;

        try {
            hospital = gson.fromJson(jsonString, Hospital.class);
            hospital.setHospital_id(hospital_id); // Keep the original hospital ID

            if (hospital.getHospital_name() == null) { // Assuming Hospital has a method to get the name
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Missing required fields\"}");
                return;
            }

            iHospitalDAO.updateHospital(hospital);
            response.getWriter().write("{\"message\": \"Hospital updated successfully\"}");
        } catch (JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid JSON format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"An error occurred: " + e.getMessage() + "\"}");
        }
    }

    /**
     * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	CorsUtil.addCorsHeaders(response);
    	response.setContentType("application/json");
        String action = request.getParameter("action");
        String hospitalIDStr = request.getParameter("id");

        if (action == null || hospitalIDStr == null) {
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

            int hospital_id = Integer.parseInt(hospitalIDStr.trim());
            iHospitalDAO.deleteHospital(hospital_id);
            response.getWriter().write("{\"message\": \"Hospital deleted successfully\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid Hospital ID format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"An error occurred: " + e.getMessage() + "\"}");
        }
    }
}
