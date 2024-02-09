package com.integrativeproyect.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	private JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		return httpSecurity
				.cors(cors -> cors.configurationSource(req -> {
					CorsConfiguration conf =new CorsConfiguration();
					conf.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
					conf.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
					conf.setAllowedHeaders(Arrays.asList("*"));
					conf.setMaxAge(3600L);

					return conf;
				}))
				.csrf(config -> config.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeHttpRequests(req -> {
					req
						.requestMatchers("/api/v0.1/auth/**", "/api/v0.1/group/**", "/api/v0.1/admin/listUserByRoleForUpdateLender", "/api/v0.1/loanCategory", "/api/v0.1/admin/registerBankAccountByBorrower", "/api/v0.1/admin/listAllBankAccount", "api/v0.1/admin/**").permitAll()
						.requestMatchers("/api/v0.1/admin/listUserByRole").hasAuthority("ROLE_ADMIN")
						.requestMatchers("/api/v0.1/document", "/api/v0.1/admin").hasAnyAuthority("ROLE_ADMIN", "ROLE_LENDER_BOSS")
						.requestMatchers("/api/v0.1/admin/registerRequestLoanByBorrower").hasAnyAuthority("ROLE_BORROWER")
						.anyRequest().authenticated();
				})
				.build();

	}

	

}
