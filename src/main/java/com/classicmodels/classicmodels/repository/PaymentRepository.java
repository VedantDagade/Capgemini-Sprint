package com.classicmodels.classicmodels.repository;

import com.classicmodels.classicmodels.entity.Payment;
import com.classicmodels.classicmodels.entity.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {

    // changed: was findByIdCustomerNumber(Integer)
    List<Payment> findByCustomer_CustomerNumber(Integer customerNumber);

    @Query("SELECT SUM(p.amount) FROM Payment p")
    BigDecimal sumAllPaymentAmounts();
}