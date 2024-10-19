package com.bs.interfaces;

import java.util.List;

import com.bs.model.Payment;

public interface IPaymentDAO {
	
	void insertPayment(Payment payment);

    List<Payment> selectAllPayments();

    Payment selectPayment(int payment_id);

    boolean updatePayment(Payment payment);

    void deletePayment(int payment_id);
}
