package com.bs.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.bs.model.Payment;
import java.util.Date;

public class PaymentDAOTest {

    private PaymentDAO paymentDAO;
    private Payment testPayment;

    @BeforeEach
    public void setUp() {
        paymentDAO = new PaymentDAO();
        // Assuming this test creates and inserts a new Payment for testing
        testPayment = new Payment(162, 2400, new Date(), "Cash");
        paymentDAO.insertPayment(testPayment); // Insert the test payment into the database
    }

    @AfterEach
    public void tearDown() {
        // Cleanup: Remove the test payment after each test (if necessary)
        if (testPayment != null) {
            paymentDAO.deletePayment(testPayment.getPayment_id());
        }
    }

    // Positive Test: Successfully update a payment
    @Test
    public void testUpdatePayment_Success() {
        // Update the payment details
        testPayment.setAmount(2400);
        testPayment.setPayment_method("Credit Card");

        try {
            paymentDAO.updatePayment(testPayment);
        } catch (Exception e) {
            Assertions.fail("Update should not throw an exception");
        }

        // Retrieve the updated payment
        Payment updatedPayment = paymentDAO.selectPayment(testPayment.getPayment_id());
        Assertions.assertNotNull(updatedPayment, "Updated payment should not be null");
        Assertions.assertEquals(2400, updatedPayment.getAmount(), "Payment amount should be updated to 2400");
        Assertions.assertEquals("Credit Card", updatedPayment.getPayment_method(), "Payment method should be updated to Credit Card");
    }

 // Negative Test: Attempting to update a non-existent payment
    @Test
    public void testUpdatePayment_NonExistentPayment() {
        // Create a payment with a non-existent ID
        Payment nonExistentPayment = new Payment(9999, 500, new Date(), "Cash");
        
        // Attempt to update and capture the outcome
        boolean result = paymentDAO.updatePayment(nonExistentPayment);
        
        // Assert that the update failed
        Assertions.assertFalse(result, "Update should return false for non-existent payment");
    }

    // Negative Test: Attempting to update a payment with an invalid amount (negative value)
    @Test
    public void testUpdatePayment_InvalidAmount() {
        // Update the payment with a negative amount
        testPayment.setAmount(-100);
        
        // Attempt to update and capture the outcome
        boolean result = paymentDAO.updatePayment(testPayment);
        
        // Assert that the update failed (this depends on your DAO's implementation)
        Assertions.assertFalse(result, "Update should return false for negative amount");
    }

    // Negative Test: Attempting to update a payment with an invalid method (empty)
    @Test
    public void testUpdatePayment_InvalidMethod() {
        // Update the payment with an empty payment method
        testPayment.setPayment_method("");
        
        // Attempt to update and capture the outcome
        boolean result = paymentDAO.updatePayment(testPayment);
        
        // Assert that the update failed (this depends on your DAO's implementation)
        Assertions.assertFalse(result, "Update should return false for empty payment method");
    }

}
