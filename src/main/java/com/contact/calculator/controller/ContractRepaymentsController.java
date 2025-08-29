package com.contact.calculator.controller;

import com.contact.calculator.model.domain.CustomerDetails;
import com.contact.calculator.model.domain.PaymentResult;
import com.contact.calculator.service.CustomerManagement;
import com.contact.calculator.service.calculator.contract.repayments.CalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Contract Repayments Calculator",
		description = "Web interface and API endpoints for device contract repayment calculations")
@Controller
@RequiredArgsConstructor
@Validated
public class ContractRepaymentsController {

	private final CustomerManagement customerManagement;

	private final CalculatorService calculatorService;

	// Web MVC Endpoints
	@GetMapping("/")
	public String redirectToCalculator() {
		return "redirect:/calculator";
	}

	@GetMapping("/calculator")
	public String showCalculator(Model model) {
		return "index";
	}

	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("customerDetails", new CustomerDetails());
		return "register";
	}

	@GetMapping("/calculate")
	public String calculateResults(@RequestParam double amount, Model model) {
		try {
			if (amount <= 0) {
				model.addAttribute("error", "Amount must be greater than zero");
				return "index";
			}

			List<PaymentResult> results = calculatorService.calculatePayments(amount);
			model.addAttribute("deviceDetailsResults", results);
			model.addAttribute("amount", amount);
			return "results";
		}
		catch (Exception e) {
			log.error("Error calculating payments: ", e);
			model.addAttribute("error", "Error processing calculation");
			return "index";
		}
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("customerDetails") CustomerDetails customerDetails,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		try {
			CustomerDetails existingUser = customerManagement.getCustomerDetails(customerDetails.getEmail());
			if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
				model.addAttribute("error", "Email already registered");
				return "register";
			}
			customerManagement.registerUser(customerDetails);
			model.addAttribute("success", "Registration successful! Please login.");
			return "login";
		}
		catch (Exception e) {
			log.error("Error registering user: ", e);
			model.addAttribute("error", "Error processing registration");
			return "register";
		}
	}

	// REST API Endpoints
	@Operation(summary = "Calculate repayments",
			description = "Calculate monthly repayments for different contract periods")
	@ApiResponse(responseCode = "200", description = "Repayments calculated successfully")
	@ApiResponse(responseCode = "400", description = "Invalid amount provided")
	@GetMapping("/api/v1/contracts/repayments")
	@ResponseBody
	public ResponseEntity<List<PaymentResult>> calculateRepayments(
			@Parameter(description = "Device amount", example = "1000.00") @RequestParam @Min(value = 1,
					message = "Amount must be greater than 0") double amount) {

		log.info("Calculating repayments for amount: {}", amount);
		List<PaymentResult> results = calculatorService.calculatePayments(amount);
		return ResponseEntity.ok(results);
	}

	@Operation(summary = "Register user via API", description = "Register a new user in the system via REST API")
	@ApiResponse(responseCode = "201", description = "User registered successfully")
	@ApiResponse(responseCode = "400", description = "Invalid user data or email already exists")
	@PostMapping("/api/v1/contracts/register")
	@ResponseBody
	public ResponseEntity<String> registerUserApi(@Valid @RequestBody CustomerDetails customerDetails) {
		log.info("Registering new user via API with email: {}", customerDetails.getEmail());

		try {
			CustomerDetails existingUser = customerManagement.getCustomerDetails(customerDetails.getEmail());
			if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
				return ResponseEntity.badRequest().body("Email already registered");
			}

			customerManagement.registerUser(customerDetails);
			return ResponseEntity.status(201).body("User registered successfully");
		}
		catch (Exception e) {
			log.error("Error registering user via API: ", e);
			return ResponseEntity.badRequest().body("Error processing registration");
		}
	}

}