package com.bs.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.bs.model.Patient;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import com.bs.dao.PatientDAO;
import com.bs.interfaces.IPatientDAO;
import com.bs.utility.CorsUtil;

@WebServlet("/patients")
public class PatientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IPatientDAO iPatientDAO = new PatientDAO();
    private Gson gson = new Gson(); 

    public PatientServlet() {
        super();
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CorsUtil.addCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        CorsUtil.addCorsHeaders(response);
        response.setContentType("application/json");

        if (action == null) {
            List<Patient> patients = iPatientDAO.selectAllPatients();
            response.getWriter().write(gson.toJson(patients));
        } else if ("edit".equals(action)) {
            handleEditPatient(request, response);
        }
    }

    private void handleEditPatient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing patient ID\"}");
            return;
        }

        try {
            int patient_id = Integer.parseInt(idParam.trim());
            Patient patient = iPatientDAO.selectPatient(patient_id);
            if (patient != null) {
                response.getWriter().write(gson.toJson(patient));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Patient not found\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid patient ID format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"An unexpected error occurred\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CorsUtil.addCorsHeaders(response);
        response.setContentType("application/json");
        String action = request.getParameter("action");

        String jsonString = getRequestBody(request);
        Patient patient;

        try {
            patient = gson.fromJson(jsonString, Patient.class);

            // Validate patient data
            if (patient.getPatient_name() == null || patient.getPatient_name().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Patient name is required\"}");
                return;
            }

            if ("create".equals(action)) {
                iPatientDAO.insertPatient(patient, patient.getUser_id()); // Use user_id from patient object
                response.getWriter().write("{\"status\": \"success\", \"message\": \"Patient created successfully\"}");
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



    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CorsUtil.addCorsHeaders(response);
        response.setContentType("application/json");

        String patientIdParam = request.getParameter("patient_id");
        if (patientIdParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing patient ID\"}");
            return;
        }

        int patient_id;
        try {
            patient_id = Integer.parseInt(patientIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid Patient ID format\"}");
            return;
        }

        String jsonString = getRequestBody(request);
        Patient patient;

        try {
            patient = gson.fromJson(jsonString, Patient.class);
            patient.setPatient_id(patient_id);

            if (patient.getPatient_name() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Missing required fields\"}");
                return;
            }

            iPatientDAO.updatePatient(patient);
            response.getWriter().write("{\"message\": \"Patient updated successfully\"}");
        } catch (JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid JSON format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"An error occurred: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CorsUtil.addCorsHeaders(response);
        response.setContentType("application/json");
        String action = request.getParameter("action");
        String patientIDStr = request.getParameter("id");

        if (action == null || patientIDStr == null) {
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

            int patientID = Integer.parseInt(patientIDStr.trim());
            iPatientDAO.deletePatient(patientID);
            response.getWriter().write("{\"message\": \"Patient deleted successfully\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid Patient ID format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"An error occurred: " + e.getMessage() + "\"}");
        }
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }
        return jsonBuffer.toString();
    }
}
