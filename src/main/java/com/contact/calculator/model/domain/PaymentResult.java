package com.contact.calculator.model.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResult {
    private int paymentPeriods;
    private double monthlyPayments;
    private double total;
}
