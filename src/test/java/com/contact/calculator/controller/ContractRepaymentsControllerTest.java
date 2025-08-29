package com.contact.calculator.controller;

import com.contact.calculator.model.domain.CustomerDetails;
import com.contact.calculator.model.domain.PaymentResult;
import com.contact.calculator.service.CustomerManagement;
import com.contact.calculator.service.calculator.contract.repayments.CalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContractRepaymentsController.class)
class ContractRepaymentsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CalculatorService calculatorService;

	@MockBean
	private CustomerManagement customerManagement;

	@Test
	@WithMockUser
	void calculateRepayments_ValidAmount_ReturnsSuccessful() throws Exception {
		double amount = 1000.00;

		List<PaymentResult> expectedResults = Arrays.asList(
				PaymentResult.builder().paymentPeriods(12).monthlyPayments(87.50).build(),
				PaymentResult.builder().paymentPeriods(24).monthlyPayments(45.83).build(),
				PaymentResult.builder().paymentPeriods(36).monthlyPayments(31.94).build());

		when(calculatorService.calculatePayments(amount)).thenReturn(expectedResults);

		mockMvc
			.perform(get("/api/v1/contracts/repayments").param("amount", String.valueOf(amount))
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].paymentPeriods").value(12))
			.andExpect(jsonPath("$[0].monthlyPayments").value(87.50));
	}

	@Test
	@WithMockUser
	void calculateRepayments_InvalidAmount_ReturnsBadRequest() throws Exception {
		when(calculatorService.calculatePayments(-1000.00))
			.thenThrow(new IllegalArgumentException("Amount must be greater than zero"));

		mockMvc
			.perform(get("/api/v1/contracts/repayments").param("amount", "-1000.00")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser
	void registerUser_ValidUser_ReturnsSuccessful() throws Exception {
		CustomerDetails userDto = CustomerDetails.builder()
			.email("test@example.com")
			.name("Test User")
			.password("password123")
			.build();

		when(customerManagement.getCustomerDetails("test@example.com")).thenReturn(null);

		mockMvc
			.perform(post("/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("email", "test@example.com")
				.param("name", "Test User")
				.param("password", "password123")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(status().isOk());
	}

}
