package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.PaymentDTO;
import com.classicmodels.classicmodels.entity.Payment;
import com.classicmodels.classicmodels.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByCustomer(@PathVariable Integer id) {
        return ResponseEntity.ok(paymentService.getPaymentsByCustomer(id));
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@Valid @RequestBody Payment payment) {
        return new ResponseEntity<>(paymentService.createPayment(payment), HttpStatus.CREATED);
    }

    @DeleteMapping("/{cNo}/{checkNo}")
    public ResponseEntity<Map<String, String>> deletePayment(
            @PathVariable Integer cNo,
            @PathVariable String checkNo) {
        paymentService.deletePayment(cNo, checkNo);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Payment deleted successfully for customerNumber: "
                + cNo + " and checkNumber: " + checkNo);
        return ResponseEntity.ok(response);
    }
}