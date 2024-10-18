package com.bs.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.bs.model.PatientRecord;
import com.bs.dao.PatientRecordDAO;
import com.bs.interfaces.IPatientRecordDAO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import com.bs.utility.CorsUtil;

@WebServlet("/patientRecords")
public class PatientRecordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IPatientRecordDAO iPatientRecordDAO = new PatientRecordDAO();
    private Gson gson = new Gson();

    public PatientRecordServlet() {
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
            List<PatientRecord> records = iPatientRecordDAO.selectAllPatientRecords();
            response.getWriter().write(gson.toJson(records));
        } else if ("view".equals(action)) {
            handleViewPatientRecord(request, response);
        }
    }

    private void handleViewPatientRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing record ID\"}");
            return;
        }

        try {
            int record_id = Integer.parseInt(idParam.trim());
            PatientRecord record = iPatientRecordDAO.selectPatientRecord(record_id);
            if (record != null) {
                response.getWriter().write(gson.toJson(record));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Patient record not found\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid record ID format\"}");
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
        PatientRecord record;

        try {
            record = gson.fromJson(jsonString, PatientRecord.class);

            if ("create".equals(action)) {
                iPatientRecordDAO.insertPatientRecord(record);
                response.getWriter().write("{\"message\": \"Patient record created successfully\"}");
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

        String recordIdParam = request.getParameter("record_id");
        if (recordIdParam == null || recordIdParam.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing record ID\"}");
            return;
        }

        try {
            int record_id = Integer.parseInt(recordIdParam.trim());
            String jsonString = getRequestBody(request);
            PatientRecord record = gson.fromJson(jsonString, PatientRecord.class);
            record.setRecord_id(record_id); // Keep the original record ID

            if (record.getDiagnosis() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Missing required fields\"}");
                return;
            }

            iPatientRecordDAO.updatePatientRecord(record);
            response.getWriter().write("{\"message\": \"Patient record updated successfully\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid record ID format\"}");
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
        String recordIDStr = request.getParameter("id");

        if (action == null || recordIDStr == null) {
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

            int recordID = Integer.parseInt(recordIDStr.trim());
            iPatientRecordDAO.deletePatientRecord(recordID);
            response.getWriter().write("{\"message\": \"Patient record deleted successfully\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid record ID format\"}");
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
