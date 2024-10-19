package com.bs.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class PaymentModelTest {

    // Positive Test: Payment creation and getters
    @Test
    public void testPaymentCreationAndGetters() {
        Date paymentDate = new Date();
        Payment payment = new Payment(163, 100.50, paymentDate, "Credit Card");

        Assertions.assertEquals(163, payment.getPayment_id());
        Assertions.assertEquals(100.50, payment.getAmount(), 0.001);
        Assertions.assertEquals(paymentDate, payment.getPayment_date());
        Assertions.assertEquals("Credit Card", payment.getPayment_method());
    }

    // Positive Test: Setting a valid amount
    @Test
    public void testSetAmount() {
        Payment payment = new Payment();
        payment.setAmount(200.75);

        Assertions.assertEquals(200.75, payment.getAmount(), 0.001);
    }

    // Positive Test: Payment date is not null
    @Test
    public void testPaymentDateNotNull() {
        Payment payment = new Payment(2, 300.00, new Date(), "Debit Card");

        Assertions.assertNotNull(payment.getPayment_date());
    }

    // Positive Test: Valid payment method
    @Test
    public void testPaymentMethodValid() {
        Payment payment = new Payment();
        payment.setPayment_method("Paypal");

        Assertions.assertEquals("Paypal", payment.getPayment_method());
    }

    // Positive Test: Payment ID is positive
    @Test
    public void testPaymentIdIsPositive() {
        Payment payment = new Payment();
        payment.setPayment_id(10);

        Assertions.assertTrue(payment.getPayment_id() > 0);
    }

    // Negative Test: Invalid amount (negative value)
    @Test
    public void testSetNegativeAmount() {
        Payment payment = new Payment();
        payment.setAmount(-50.00);

        Assertions.assertFalse(payment.getAmount() >= 0);
    }

    // Negative Test: Null payment method
    @Test
    public void testPaymentMethodIsNull() {
        Payment payment = new Payment();
        payment.setPayment_method(null);

        Assertions.assertNull(payment.getPayment_method());
    }

    // Negative Test: Null payment date
    @Test
    public void testPaymentDateIsNull() {
        Payment payment = new Payment();
        payment.setPayment_date(null);

        Assertions.assertNull(payment.getPayment_date());
    }

    // Negative Test: Fail on invalid Payment ID
    @Test
    public void testFailOnInvalidPaymentId() {
        Payment payment = new Payment();
        payment.setPayment_id(-1);

        if (payment.getPayment_id() < 0) {
            Assertions.fail("Payment ID should not be negative");
        }
    }

    // Negative Test: Empty payment method
    @Test
    public void testEmptyPaymentMethod() {
        Payment payment = new Payment();
        payment.setPayment_method("");

        Assertions.assertFalse(payment.getPayment_method() != null && !payment.getPayment_method().trim().isEmpty());
    }
}
