package com.bs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.bs.interfaces.IPaymentDAO;
import com.bs.model.Payment;
import com.bs.utility.DBConnection;

public class PaymentDAO implements IPaymentDAO {

    // SQL Queries for Payment operations
    public static final String SELECT_PAYMENT_BY_ID = "SELECT * FROM payment WHERE payment_id = ?";
    public static final String SELECT_ALL_PAYMENTS = "SELECT * FROM payment";
    public static final String INSERT_PAYMENT = "INSERT INTO payment (amount, payment_date, payment_method) VALUES (?, ?, ?)";
    public static final String UPDATE_PAYMENT = "UPDATE payment SET amount=?, payment_date=?, payment_method=? WHERE payment_id = ?";
    public static final String DELETE_PAYMENT = "DELETE FROM payment WHERE payment_id = ?";

    @Override
    public void insertPayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null.");
        }
        
        // Validate payment details
        validatePayment(payment);

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_PAYMENT, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, payment.getAmount());
            stmt.setDate(2, new java.sql.Date(payment.getPayment_date().getTime()));
            stmt.setString(3, payment.getPayment_method());

            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payment.setPayment_id(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            System.err.println("Error inserting payment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Payment> selectAllPayments() {
        List<Payment> payments = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ALL_PAYMENTS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int payment_id = rs.getInt("payment_id");
                double amount = rs.getDouble("amount");
                java.sql.Date payment_date = rs.getDate("payment_date");
                String payment_method = rs.getString("payment_method");

                Payment payment = new Payment(payment_id, amount, new java.util.Date(payment_date.getTime()), payment_method);
                payments.add(payment);
            }
        } catch (Exception e) {
            System.err.println("Error selecting all payments: " + e.getMessage());
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public Payment selectPayment(int payment_id) {
//        if (payment_id <= 0) {
//            throw new IllegalArgumentException("Payment ID must be a positive integer.");
//        }

        Payment payment = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_PAYMENT_BY_ID)) {

            stmt.setInt(1, payment_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double amount = rs.getDouble("amount");
                    java.sql.Date payment_date = rs.getDate("payment_date");
                    String payment_method = rs.getString("payment_method");

                    payment = new Payment(payment_id, amount, new java.util.Date(payment_date.getTime()), payment_method);
                }
            }
        } catch (Exception e) {
            System.err.println("Error selecting payment by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return payment;
    }

    @Override
    public boolean updatePayment(Payment payment) {
    	// Validate payment details
        validatePayment(payment);
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null.");
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_PAYMENT)) {

            stmt.setDouble(1, payment.getAmount());
            stmt.setDate(2, new java.sql.Date(payment.getPayment_date().getTime()));
            stmt.setString(3, payment.getPayment_method());
            stmt.setInt(4, payment.getPayment_id());

            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error updating payment: " + e.getMessage());
            e.printStackTrace();
        }
		return false;
    }

    @Override
    public void deletePayment(int payment_id) {
        if (payment_id <= 0) {
            throw new IllegalArgumentException("Payment ID must be a positive integer.");
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_PAYMENT)) {

            stmt.setInt(1, payment_id);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error deleting payment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Validation method for payment
    void validatePayment(Payment payment) {
        if (payment.getAmount() < 0) {
            throw new IllegalArgumentException("Payment amount cannot be negative.");
        }
        if (payment.getPayment_date() == null) {
            throw new IllegalArgumentException("Payment date cannot be null.");
        }
        if (payment.getPayment_method() == null || payment.getPayment_method().trim().isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be null or empty.");
        }
    }
}
