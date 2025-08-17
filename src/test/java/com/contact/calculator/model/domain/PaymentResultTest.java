package com.contact.calculator.model.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentResultTest {

    @Test
    void constructor_ValidInput_CreatesObject() {
        int months = 12;
        double payment = 100.00;

        PaymentResult result = PaymentResult.builder()
                .paymentPeriods(months)
                .monthlyPayments(payment)
                .build();


        assertEquals(months, result.getPaymentPeriods());
        assertEquals(payment, result.getMonthlyPayments(), 0.01);
    }

}