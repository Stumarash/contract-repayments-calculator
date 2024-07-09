package com.contact.calculator.service.calculator.contract.repayments;

import com.contact.calculator.model.domain.DeviceDetails;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface RepaymentsCalculator {

    List<DeviceDetails> calculateDevicePayment(DeviceDetails deviceDetails) throws JsonProcessingException;
}
