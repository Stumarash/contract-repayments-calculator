package com.contact.calculator;

import com.contact.calculator.model.domain.PaymentResult;
import com.contact.calculator.service.calculator.contract.repayments.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContractRepaymentsCalculatorApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CalculatorService calculatorService;

	@Test
	void contextLoads() {
	}

	@Test
	@WithMockUser
	void fullCalculationFlow_Success() throws Exception {
		// Given
		double amount = 1000.00;
		List<PaymentResult> expectedResults = Arrays.asList(
				PaymentResult.builder().paymentPeriods(12).monthlyPayments(87.50).build(),
				PaymentResult.builder().paymentPeriods(24).monthlyPayments(45.83).build(),
				PaymentResult.builder().paymentPeriods(36).monthlyPayments(31.94).build());

		when(calculatorService.calculatePayments(anyDouble())).thenReturn(expectedResults);

		// When & Then
		mockMvc.perform(get("/api/v1/contracts/repayments").param("amount", String.valueOf(amount)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].paymentPeriods").value(12))
			.andExpect(jsonPath("$[0].monthlyPayments").value(87.50));
	}

}