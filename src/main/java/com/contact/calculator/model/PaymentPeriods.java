package com.contact.calculator.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentPeriods {

    TWELVE_MONTHS_PERIOD(12, "Twelve Months Period"),
    TWENTY_FOUR_MONTHS_PERIOD(24, "24 Months Period"),
    THIRTY_SIX_MONTHS_PERIOD(36, "36 Months Period");

    private final int period;
    private final String description;

}
