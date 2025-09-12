package com.walking.tree.parking.service;

import com.walking.tree.parking.exception.PaymentException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class PaymentService {

    // Simulate charging card. Replace with gateway integration.
    public void charge(String cardNumber, BigDecimal amount) {
        // basic validation
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new PaymentException("Card information missing");
        }

        // simulate random failure only for demo (remove in prod)
        boolean randomFail = new Random().nextInt(100) < 5; // 5% fail
        if (randomFail) {
            throw new PaymentException("Simulated card decline");
        }

        // else succeed (in real life call gateway & handle response)
    }
}
