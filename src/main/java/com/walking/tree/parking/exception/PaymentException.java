package com.walking.tree.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class PaymentException extends RuntimeException {
    public PaymentException(String msg) { super(msg); }
}
