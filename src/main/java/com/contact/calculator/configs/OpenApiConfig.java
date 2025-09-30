package com.contact.calculator.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new Info().title("Contract Repayments Calculator API").version("1.0.0").description("""
					API for calculating device contract repayments with the following features:
					* User registration and authentication
					* Device repayment calculations over 12, 24, and 36 months
					* Fixed interest rate of 6.5%
					* Secure endpoints with Basic Authentication
					"""))
			.components(new Components().addSecuritySchemes("basic",
					new SecurityScheme().type(SecurityScheme.Type.HTTP)
						.scheme("basic")
						.description("Basic authentication with username and password")));
	}

}