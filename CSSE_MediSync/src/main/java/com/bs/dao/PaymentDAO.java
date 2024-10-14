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
    private static final String SELECT_PAYMENT_BY_ID = "SELECT * FROM payment WHERE payment_id = ?";
    private static final String SELECT_ALL_PAYMENTS = "SELECT * FROM payment";
    private static final String INSERT_PAYMENT = "INSERT INTO payment (amount, payment_date, payment_method) VALUES (?, ?, ?)";
    private static final String UPDATE_PAYMENT = "UPDATE payment SET amount=?, payment_date=?, payment_method=? WHERE payment_id = ?";
    private static final String DELETE_PAYMENT = "DELETE FROM payment WHERE payment_id = ?";

    @Override
    public void insertPayment(Payment payment) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_PAYMENT)) {

            stmt.setDouble(1, payment.getAmount());
            stmt.setDate(2, new java.sql.Date(payment.getPayment_date().getTime()));
            stmt.setString(3, payment.getPayment_method());

            stmt.executeUpdate();
        } catch (Exception e) {
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
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public Payment selectPayment(int payment_id) {
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
            e.printStackTrace();
        }
        return payment;
    }

    @Override
    public void updatePayment(Payment payment) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(UPDATE_PAYMENT)) {

            stmt.setDouble(1, payment.getAmount());
            stmt.setDate(2, new java.sql.Date(payment.getPayment_date().getTime()));
            stmt.setString(3, payment.getPayment_method());
            stmt.setInt(4, payment.getPayment_id());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePayment(int payment_id) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_PAYMENT)) {

            stmt.setInt(1, payment_id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
