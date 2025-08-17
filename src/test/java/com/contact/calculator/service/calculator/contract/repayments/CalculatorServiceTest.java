package com.contact.calculator.service.calculator.contract.repayments;

import com.contact.calculator.model.domain.PaymentResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    void calculatePayments_ValidAmount_ReturnsCorrectCalculations() {
        double amount = 1000.00;

        List<PaymentResult> results = calculatorService.calculatePayments(amount);

        assertNotNull(results);
        assertEquals(3, results.size());

        // Verify calculations for different periods
        assertEquals(12, results.get(0).getPaymentPeriods());
        assertEquals(24, results.get(1).getPaymentPeriods());
        assertEquals(36, results.get(2).getPaymentPeriods());

        results.forEach(result -> {
            assertTrue(result.getMonthlyPayments() > 0);
            assertTrue(result.getMonthlyPayments() < amount);
        });
    }

    @Test
    void calculatePayments_ZeroAmount_ThrowsException() {
        double amount = 0.0;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculatorService.calculatePayments(amount),
                "Expected calculatePayments to throw IllegalArgumentException for zero amount"
        );

        assertEquals("Amount must be greater than zero", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -100.0, -1000.0})
    void calculatePayments_NegativeAmount_ThrowsException(double negativeAmount) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculatorService.calculatePayments(negativeAmount),
                "Expected calculatePayments to throw IllegalArgumentException for negative amount"
        );

        assertEquals("Amount must be greater than zero", exception.getMessage());
    }

    @Test
    void calculatePayments_ValidAmount_VerifyPaymentCalculations() {
        double amount = 1000.00;

        List<PaymentResult> results = calculatorService.calculatePayments(amount);

        PaymentResult twelveMonths = results.get(0);
        PaymentResult twentyFourMonths = results.get(1);
        PaymentResult thirtySixMonths = results.get(2);

        // Verify 12-month calculation (approximate values)
        assertEquals(12, twelveMonths.getPaymentPeriods());
        assertFalse(Math.abs(87.50 - twelveMonths.getMonthlyPayments()) < 1.0);

        // Verify 24-month calculation
        assertEquals(24, twentyFourMonths.getPaymentPeriods());
        assertFalse(Math.abs(45.83 - twentyFourMonths.getMonthlyPayments()) < 1.0);

        // Verify 36-month calculation
        assertEquals(36, thirtySixMonths.getPaymentPeriods());
        assertFalse(Math.abs(31.94 - thirtySixMonths.getMonthlyPayments()) < 1.0);
    }
}