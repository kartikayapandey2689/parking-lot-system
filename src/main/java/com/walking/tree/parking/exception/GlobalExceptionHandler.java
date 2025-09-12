package com.walking.tree.parking.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
        ApiError err = new ApiError(HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ApiError> handlePayment(PaymentException ex, HttpServletRequest req) {
        ApiError err = new ApiError(HttpStatus.PAYMENT_REQUIRED.value(), "Payment Error", ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .findFirst().orElse(ex.getMessage());
        ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation Failed", msg, req.getRequestURI());
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex, HttpServletRequest req) {
        ApiError err = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error", ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}
