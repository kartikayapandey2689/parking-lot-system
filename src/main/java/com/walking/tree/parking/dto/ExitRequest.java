package com.walking.tree.parking.dto;

import jakarta.validation.constraints.NotNull;

public class ExitRequest {
    @NotNull
    private Long ticketId;

    private PaymentInfo paymentInfo;

    // nested DTO
    public static class PaymentInfo {
        private String cardNumber;
        private String expiry;
        private String cvv;
        // getters/setters
        public String getCardNumber() { return cardNumber; }
        public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
        public String getExpiry() { return expiry; }
        public void setExpiry(String expiry) { this.expiry = expiry; }
        public String getCvv() { return cvv; }
        public void setCvv(String cvv) { this.cvv = cvv; }
    }

    // getters/setters
    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }
    public PaymentInfo getPaymentInfo() { return paymentInfo; }
    public void setPaymentInfo(PaymentInfo paymentInfo) { this.paymentInfo = paymentInfo; }
}
