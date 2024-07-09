package com.contact.calculator.service;

import com.contact.calculator.model.domain.CustomerDetails;

public interface CustomerManagement {

    void registerUser(CustomerDetails customerDetails);

    CustomerDetails getCustomerDetails(String username);
}
