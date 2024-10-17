package com.bs.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.bs.model.Payment;
import com.bs.dao.PaymentDAO;
import com.bs.interfaces.IPaymentDAO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import com.bs.utility.CorsUtil;

@WebServlet("/payments")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IPaymentDAO iPaymentDAO = new PaymentDAO();
    private Gson gson = new Gson(); 

    public PaymentServlet() {
        super();
    }

    // Handle GET requests (view all or specific payment)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        CorsUtil.addCorsHeaders(response);
        response.setContentType("application/json");

        if (action == null) {
            // Return all payments if no specific action
            List<Payment> payments = iPaymentDAO.selectAllPayments();
            response.getWriter().write(gson.toJson(payments));
        } else if ("view".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Missing payment ID\"}");
                return;
            }

            try {
                int payment_id = Integer.parseInt(idParam.trim());
                Payment payment = iPaymentDAO.selectPayment(payment_id);
                if (payment != null) {
                    response.getWriter().write(gson.toJson(payment));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"Payment not found\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Invalid payment ID format\"}");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"An unexpected error occurred\"}");
            }
        }
    }

    // Handle POST requests (create new payment)
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
        Payment payment;

        try {
            payment = gson.fromJson(jsonString, Payment.class);
            if ("create".equals(action)) {
                iPaymentDAO.insertPayment(payment);
                response.getWriter().write("{\"message\": \"Payment created successfully\"}");
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

    // Handle PUT requests (update existing payment)
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	CorsUtil.addCorsHeaders(response);
    	response.setContentType("application/json");

        String paymentIdParam = request.getParameter("payment_id");
        if (paymentIdParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing payment ID\"}");
            return;
        }

        try {
            int payment_id = Integer.parseInt(paymentIdParam.trim());
            StringBuilder jsonBuffer = new StringBuilder();
            String line;

            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }

            String jsonString = jsonBuffer.toString();
            Payment payment = gson.fromJson(jsonString, Payment.class);
            payment.setPayment_id(payment_id); // Keep the original payment ID

            if (payment.getPayment_date() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Missing required fields\"}");
                return;
            }

            iPaymentDAO.updatePayment(payment);
            response.getWriter().write("{\"message\": \"Payment updated successfully\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid payment ID format\"}");
        } catch (JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid JSON format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"An error occurred: " + e.getMessage() + "\"}");
        }
    }

    // Handle DELETE requests (delete payment)
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	CorsUtil.addCorsHeaders(response);
    	response.setContentType("application/json");
        String action = request.getParameter("action");
        String paymentIDStr = request.getParameter("id");

        if (action == null || paymentIDStr == null) {
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

            int paymentID = Integer.parseInt(paymentIDStr.trim());
            iPaymentDAO.deletePayment(paymentID);
            response.getWriter().write("{\"message\": \"Payment deleted successfully\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid payment ID format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"An error occurred: " + e.getMessage() + "\"}");
        }
    }
}
