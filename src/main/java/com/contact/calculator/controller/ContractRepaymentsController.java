package com.contact.calculator.controller;

import com.contact.calculator.model.domain.CustomerDetails;
import com.contact.calculator.model.domain.DeviceDetails;
import com.contact.calculator.model.domain.PaymentResult;
import com.contact.calculator.service.CustomerManagement;
import com.contact.calculator.service.calculator.contract.repayments.CalculatorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Contract Repayments Calculator", description = "API endpoints and web interface for device contract repayment calculations")
@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
@Validated
public class ContractRepaymentsController {

    private final CustomerManagement customerManagement;
    private final CalculatorService calculatorService;

    @GetMapping
    public String showCalculator(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        // Add empty customerDetails to prevent null pointer
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
        } catch (Exception e) {
            log.error("Error calculating payments: ", e);
            model.addAttribute("error", "Error processing calculation");
            return "index";
        }
    }


    @PostMapping("/users")
    public String registerUser(@Valid @ModelAttribute("customerDetails") CustomerDetails customerDetails,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            if (customerManagement.getCustomerDetails(customerDetails.getEmail()) != null) {
                model.addAttribute("error", "Email already registered");
                return "register";
            }
            customerManagement.registerUser(customerDetails);
            return "redirect:/api/v1/contracts/login";
        } catch (Exception e) {
            log.error("Error registering user: ", e);
            model.addAttribute("error", "Error processing registration");
            return "register";
        }
    }

    @GetMapping("/repayments")
    @ResponseBody
    public List<PaymentResult> calculateRepayments(@RequestParam double amount) {
        return calculatorService.calculatePayments(amount);
    }
}