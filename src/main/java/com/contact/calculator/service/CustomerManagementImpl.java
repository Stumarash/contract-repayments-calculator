package com.contact.calculator.service;

import com.contact.calculator.model.domain.CustomerDetails;
import com.contact.calculator.model.entities.CustomerDetailsEntity;
import com.contact.calculator.model.entities.RoleEntity;
import com.contact.calculator.model.mapper.UserDetailsMapper;
import com.contact.calculator.repository.CustomerDetailsRepository;
import com.contact.calculator.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerManagementImpl implements CustomerManagement {

	private final CustomerDetailsRepository customerDetailsRepository;

	private final UserDetailsMapper userDetailsMapper;

	private final PasswordEncoder passwordEncoder;

	private final RoleRepository roleRepository;

	@Override
	public void registerUser(CustomerDetails customerDetails) {
		log.info("registering customer {} on the system", customerDetails);
		var customerDetailsEntity = userDetailsMapper.map(customerDetails);
		customerDetailsEntity.setPassword(passwordEncoder.encode(customerDetails.getPassword()));
		RoleEntity roleEntity = roleRepository.findByName("USER");

		if (Objects.isNull(roleEntity)) {
			roleEntity = checkRoleExists();
		}

		customerDetailsEntity.setRoles(Collections.singletonList(roleEntity));

		customerDetailsRepository.save(customerDetailsEntity);

		log.info("customer registered");
	}

	@Override
	public CustomerDetails getCustomerDetails(String username) {
		log.info("retrieve existing customer {} on the system", username);
		CustomerDetailsEntity customerDetailsEntity = customerDetailsRepository.findByEmail(username);
		if (Objects.isNull(customerDetailsEntity)) {
			return CustomerDetails.builder().build();
		}

		return userDetailsMapper.map(customerDetailsEntity);
	}

	private RoleEntity checkRoleExists() {
		RoleEntity role = new RoleEntity();
		role.setName("USER");
		return roleRepository.save(role);
	}

}
