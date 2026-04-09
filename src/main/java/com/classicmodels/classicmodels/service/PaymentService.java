package com.classicmodels.classicmodels.service;

import com.classicmodels.classicmodels.entity.Payment;
import com.classicmodels.classicmodels.entity.PaymentId;
import com.classicmodels.classicmodels.exception.ResourceNotFoundException;
import com.classicmodels.classicmodels.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // GET all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // GET payments by customer
    public List<Payment> getPaymentsByCustomer(Integer customerNumber) {
        return paymentRepository.findByIdCustomerNumber(customerNumber);
    }

    // CREATE new payment
    public Payment createPayment(Payment payment) {
        if (paymentRepository.existsById(payment.getId())) {
            throw new RuntimeException(
                    "Payment already exists with customerNumber: "
                            + payment.getId().getCustomerNumber()
                            + " and checkNumber: "
                            + payment.getId().getCheckNumber());
        }
        return paymentRepository.save(payment);
    }

    // DELETE payment by composite key
    public void deletePayment(Integer customerNumber, String checkNumber) {
        PaymentId id = new PaymentId(customerNumber, checkNumber);
        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found with customerNumber: "
                                + customerNumber
                                + " and checkNumber: "
                                + checkNumber));
        paymentRepository.delete(existing);
    }
}