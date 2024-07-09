package com.contact.calculator.controller;

import com.contact.calculator.model.domain.CustomerDetails;
import com.contact.calculator.model.domain.DeviceDetails;
import com.contact.calculator.service.CustomerManagement;
import com.contact.calculator.service.calculator.contract.repayments.RepaymentsCalculator;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Tag(name = "Contract Repayments Calculator", description = "service to calculate contract repayments")
@RequiredArgsConstructor
@Controller
public class ContractRepaymentsController {

    private final CustomerManagement customerManagement;
    private final RepaymentsCalculator repaymentsCalculator;

    @Operation(summary = "Home page")
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @Operation(summary = "Form To Get Device Amount")
    @GetMapping("/results")
    public String showDeviceDetailsForm(Model model) {
        DeviceDetails deviceDetails = DeviceDetails.builder().build();
        model.addAttribute("deviceDetails", deviceDetails);
        return "results";
    }

    @Operation(summary = "Method to calculate contract payment options")
    @PostMapping("/results")
    public String paymentsOption(Model model, @ModelAttribute("deviceDetails") DeviceDetails deviceDetails) {
        List<DeviceDetails> deviceDetailsResults = null;
        try {
            deviceDetailsResults = repaymentsCalculator.calculateDevicePayment(deviceDetails);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("deviceDetailsResults", deviceDetailsResults);
        return "results";
    }

    @Operation(summary = "Form for User Registration")
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        CustomerDetails userDto = CustomerDetails.builder().build();
        model.addAttribute("customer", userDto);
        return "register";
    }

    @Operation(summary = "Handle user registration form submit request")
    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("customer") CustomerDetails userDto, BindingResult result, Model model) {
        CustomerDetails existingUser = customerManagement.getCustomerDetails(userDto.getEmail());
        if (existingUser != null && existingUser.getEmail() != null) {
            result.rejectValue("email", null, "there is already an account existing with this email");
        }

        if (result.hasErrors()) {
            model.addAttribute("customer", userDto);
            return "/register"; // if any form has errors it will be redirected to register page only.
        }

        customerManagement.registerUser(userDto);
        return "redirect:/register?success";
    }

    @Operation(summary = "login Page")
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
