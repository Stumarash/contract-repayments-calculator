package com.contact.calculator.model.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetails extends AbstractBaseDomain implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Name is required")
	private String name;

	private String surname;

	@NotBlank(message = "Password is required")
	private String password;

	@Email(message = "Please provide a valid email")
	@NotBlank(message = "Email is required")
	private String email;

}
