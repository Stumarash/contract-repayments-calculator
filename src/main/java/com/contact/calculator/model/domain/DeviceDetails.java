package com.contact.calculator.model.domain;

import com.contact.calculator.model.PaymentPeriods;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private double amount;
    private double monthlyPayments;
    private PaymentPeriods paymentPeriods;

    private double total = 0;

}
