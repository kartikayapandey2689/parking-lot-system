package com.walking.tree.parking.service;

import com.walking.tree.parking.exception.PaymentException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class PaymentService {

    public void charge(String cardNumber, BigDecimal amount) {
        if (cardNumber == null || cardNumber.trim().isEmpty()) throw new PaymentException("Card missing");
        boolean fail = new Random().nextInt(100) < 5; // 5% fail simulation
        if (fail) throw new PaymentException("Simulated payment failure");
    }
}
