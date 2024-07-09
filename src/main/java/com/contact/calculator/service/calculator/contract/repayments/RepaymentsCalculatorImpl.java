package com.contact.calculator.service.calculator.contract.repayments;

import com.contact.calculator.model.PaymentPeriods;
import com.contact.calculator.model.domain.DeviceDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class RepaymentsCalculatorImpl implements RepaymentsCalculator {

    @Override
    public List<DeviceDetails> calculateDevicePayment(DeviceDetails deviceDetails) throws JsonProcessingException {
        double chargedPercentage = 0.065;
        List<DeviceDetails> listOfPotentialPayments = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");


        double twelveMonthsPeriodTotal = Double.parseDouble(decimalFormat.format((deviceDetails.getAmount() * PaymentPeriods.TWELVE_MONTHS_PERIOD.getPeriod() * chargedPercentage) + deviceDetails.getAmount()).replace(",", "."));
        double twentyFourMonthsPeriodTotal = Double.parseDouble(decimalFormat.format((deviceDetails.getAmount() * PaymentPeriods.TWENTY_FOUR_MONTHS_PERIOD.getPeriod() * chargedPercentage) + deviceDetails.getAmount()).replace(",", "."));
        double thirtySixMonthsPeriodTotal = Double.parseDouble(decimalFormat.format((deviceDetails.getAmount() * PaymentPeriods.THIRTY_SIX_MONTHS_PERIOD.getPeriod() * chargedPercentage) + deviceDetails.getAmount()).replace(",", "."));

        listOfPotentialPayments.add(DeviceDetails.builder()
                .paymentPeriods(PaymentPeriods.TWELVE_MONTHS_PERIOD)
                .monthlyPayments(Double.parseDouble(decimalFormat.format(twelveMonthsPeriodTotal / PaymentPeriods.TWELVE_MONTHS_PERIOD.getPeriod()).replace(",", ".")))
                .total(twelveMonthsPeriodTotal)
                .build());

        listOfPotentialPayments.add(DeviceDetails.builder()
                .paymentPeriods(PaymentPeriods.TWENTY_FOUR_MONTHS_PERIOD)
                .monthlyPayments(Double.parseDouble(decimalFormat.format(twentyFourMonthsPeriodTotal / PaymentPeriods.TWENTY_FOUR_MONTHS_PERIOD.getPeriod()).replace(",", ".")))
                .total(twentyFourMonthsPeriodTotal)
                .build());

        listOfPotentialPayments.add(DeviceDetails.builder()
                .paymentPeriods(PaymentPeriods.THIRTY_SIX_MONTHS_PERIOD)
                .monthlyPayments(Double.parseDouble(decimalFormat.format(thirtySixMonthsPeriodTotal / PaymentPeriods.THIRTY_SIX_MONTHS_PERIOD.getPeriod()).replace(",", ".")))
                .total(thirtySixMonthsPeriodTotal)
                .build());

        log.info("Returning list of payment options \n{}", new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true).writeValueAsString(listOfPotentialPayments)
        );
        return listOfPotentialPayments;
    }
}
