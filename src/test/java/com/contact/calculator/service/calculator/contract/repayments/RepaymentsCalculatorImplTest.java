package com.contact.calculator.service.calculator.contract.repayments;

import com.contact.calculator.model.PaymentPeriods;
import com.contact.calculator.model.domain.DeviceDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class RepaymentsCalculatorImplTest {

    private final RepaymentsCalculatorImpl repaymentsCalculator;

    public RepaymentsCalculatorImplTest() {
        repaymentsCalculator = new RepaymentsCalculatorImpl();
    }

    @Test
    void calculateDevicePayment12MonthsPlan() throws Exception {

        DeviceDetails deviceDetails12Months = DeviceDetails.builder().paymentPeriods(PaymentPeriods.TWELVE_MONTHS_PERIOD).amount(12.90).build();

        List<DeviceDetails> deviceDetailsList = repaymentsCalculator.calculateDevicePayment(deviceDetails12Months);

        Assertions.assertNotNull(deviceDetailsList);
        Assertions.assertEquals(3, deviceDetailsList.size());
        Assertions.assertEquals(22.96, deviceDetailsList.get(0).getTotal());
        Assertions.assertEquals(1.91, deviceDetailsList.get(0).getMonthlyPayments());
        Assertions.assertEquals(PaymentPeriods.TWELVE_MONTHS_PERIOD, deviceDetailsList.get(0).getPaymentPeriods());

    }

    @Test
    void calculateDevicePayment24MonthsPlan() throws Exception {
        DeviceDetails deviceDetails24Months = DeviceDetails.builder().paymentPeriods(PaymentPeriods.TWENTY_FOUR_MONTHS_PERIOD).amount(12.90).build();

        List<DeviceDetails> deviceDetailsList = repaymentsCalculator.calculateDevicePayment(deviceDetails24Months);

        Assertions.assertNotNull(deviceDetailsList);
        Assertions.assertEquals(3, deviceDetailsList.size());
        Assertions.assertEquals(33.02, deviceDetailsList.get(1).getTotal());
        Assertions.assertEquals(1.38, deviceDetailsList.get(1).getMonthlyPayments());
        Assertions.assertEquals(PaymentPeriods.TWENTY_FOUR_MONTHS_PERIOD, deviceDetailsList.get(1).getPaymentPeriods());

    }

    @Test
    void calculateDevicePayment36MonthsPlan() throws Exception {
        DeviceDetails deviceDetails36Months = DeviceDetails.builder().paymentPeriods(PaymentPeriods.TWELVE_MONTHS_PERIOD).amount(12.90).build();

        List<DeviceDetails> deviceDetailsList = repaymentsCalculator.calculateDevicePayment(deviceDetails36Months);

        Assertions.assertNotNull(deviceDetailsList);
        Assertions.assertEquals(3, deviceDetailsList.size());
        Assertions.assertEquals(43.09, deviceDetailsList.get(2).getTotal());
        Assertions.assertEquals(1.2, deviceDetailsList.get(2).getMonthlyPayments());
        Assertions.assertEquals(PaymentPeriods.THIRTY_SIX_MONTHS_PERIOD, deviceDetailsList.get(2).getPaymentPeriods());
    }
}