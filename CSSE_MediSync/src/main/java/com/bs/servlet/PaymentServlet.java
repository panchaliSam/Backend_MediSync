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
    public IPaymentDAO iPaymentDAO = new PaymentDAO();
    private Gson gson = new Gson();

    public PaymentServlet() {
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
            List<Payment> payments = iPaymentDAO.selectAllPayments();
            response.getWriter().write(gson.toJson(payments));
        } else if ("view".equals(action)) {
            handleViewPayment(request, response);
        }
    }

    private void handleViewPayment(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CorsUtil.addCorsHeaders(response);
        response.setContentType("application/json");
        String action = request.getParameter("action");

        String jsonString = getRequestBody(request);
        Payment payment;

        try {
            payment = gson.fromJson(jsonString, Payment.class);

            // Validate payment data (you can add more validation as needed)
            if (payment.getPayment_date() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Payment date is required\"}");
                return;
            }
            if ("create".equals(action)) {
                iPaymentDAO.insertPayment(payment);
                response.getWriter().write("{\"status\": \"success\", \"message\": \"Payment created successfully\"}");
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

        String paymentIdParam = request.getParameter("payment_id");
        if (paymentIdParam == null || paymentIdParam.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Missing payment ID\"}");
            return;
        }

        int payment_id;
        try {
            payment_id = Integer.parseInt(paymentIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid payment ID format\"}");
            return;
        }

        String jsonString = getRequestBody(request);
        Payment payment;

        try {
            payment = gson.fromJson(jsonString, Payment.class);
//             int payment_id = Integer.parseInt(paymentIdParam.trim());
//             String jsonString = getRequestBody(request);
//             Payment payment = gson.fromJson(jsonString, Payment.class);

            payment.setPayment_id(payment_id); // Keep the original payment ID

            if (payment.getPayment_date() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Missing required fields\"}");
                return;
            }

            iPaymentDAO.updatePayment(payment);
            response.getWriter().write("{\"message\": \"Payment updated successfully\"}");
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
