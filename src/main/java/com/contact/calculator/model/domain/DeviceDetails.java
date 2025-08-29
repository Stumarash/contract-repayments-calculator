package com.contact.calculator.model.domain;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDetails {

	@Positive(message = "Amount must be greater than zero")
	private double amount;

}
