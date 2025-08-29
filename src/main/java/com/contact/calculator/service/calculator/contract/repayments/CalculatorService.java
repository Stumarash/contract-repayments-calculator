package com.contact.calculator.service.calculator.contract.repayments;

import com.contact.calculator.model.domain.PaymentResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculatorService {

	private static final double INTEREST_RATE = 6.5;

	private static final int[] PAYMENT_PERIODS = { 12, 24, 36 };

	public List<PaymentResult> calculatePayments(double amount) {
		validateAmount(amount);

		List<PaymentResult> results = new ArrayList<>();
		for (int months : PAYMENT_PERIODS) {
			double monthlyPayment = calculateMonthlyPayment(amount, months);
			double total = monthlyPayment * months;
			results.add(PaymentResult.builder()
				.paymentPeriods(months)
				.monthlyPayments(roundToTwoDecimals(monthlyPayment))
				.total(roundToTwoDecimals(total))
				.build());
		}
		return results;
	}

	private void validateAmount(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}
	}

	private double calculateMonthlyPayment(double principal, int months) {
		double monthlyInterestRate = (INTEREST_RATE / 100) / 12;
		double numerator = principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, months);
		double denominator = Math.pow(1 + monthlyInterestRate, months) - 1;
		return numerator / denominator;
	}

	private double roundToTwoDecimals(double value) {
		return Math.round(value * 100.0) / 100.0;
	}

}