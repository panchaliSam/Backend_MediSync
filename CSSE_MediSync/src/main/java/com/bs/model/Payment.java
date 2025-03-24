package com.bs.model;

import java.util.Date;

public class Payment {

    private int payment_id;  
    private double amount;
    private Date payment_date;
    private String payment_method;

    // Default constructor
    public Payment() {
        super();
    }

    // Parameterized constructor
    public Payment(int payment_id, double amount, Date payment_date, String payment_method) {
        this.payment_id = payment_id;
        this.setAmount(amount);
        this.setPayment_date(payment_date);
        this.setPayment_method(payment_method);
    }

    // Getter for payment_id
    public int getPayment_id() {
        return payment_id;
    }

    // Setter for payment_id 
    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id; 
    }

    // Getter and setter for amount
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {

        this.amount = amount;
    }

    // Getter and setter for payment_date
    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    // Getter and setter for payment_method
    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}
