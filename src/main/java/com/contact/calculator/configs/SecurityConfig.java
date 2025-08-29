package com.contact.calculator.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserDetailsService userDetailsService;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authProvider);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
			.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/register")
				.permitAll()
				.requestMatchers("/swagger-ui.html", "/api-docs/**", "/swagger-ui/**", "/swagger-resources/**",
						"/webjars/**")
				.permitAll()
				.requestMatchers("/actuator/health", "/actuator/info")
				.permitAll()
				.requestMatchers("/h2-console/**")
				.permitAll()
				.requestMatchers("/", "/calculator", "/register", "/login", "/calculate")
				.permitAll()
				.requestMatchers("/api/v1/contracts/**")
				.permitAll()
				.requestMatchers("/css/**", "/js/**", "/images/**")
				.permitAll()
				.anyRequest()
				.authenticated())
			.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/calculator", true).permitAll())
			.logout(logout -> logout.logoutSuccessUrl("/login").permitAll())
			.httpBasic(httpBasic -> {
			})
			.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
			.build();
	}

}