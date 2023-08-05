package com.codergeek.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class LoginConfig {
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests()
		.requestMatchers("/public/**","/","/images/**","/article/**","/search","/jobs").permitAll()
		.requestMatchers("/signin","/signup","/jobapp","/forgotpassword","/resetpassword").anonymous()
		.requestMatchers("/admin/**","/reviewapplications").hasRole("admin")
		.requestMatchers("/jobapp").hasAnyRole("admin","user")
		.requestMatchers("/createjob","/applications").hasRole("recruiter")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/signin")
		.defaultSuccessUrl("/")
		.and().logout()
		.and()
		.httpBasic();
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
