package com.example.congfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.FormLoginBeanDefinitionParser;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.filter.JwtGenerationFilter;
import com.example.filter.JwtValidatorFilter;
import com.example.service.UserService;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private UserService userService;
	
	
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authP=new DaoAuthenticationProvider();
		authP.setUserDetailsService(userService);
		authP.setPasswordEncoder(passwordEncoder());
		return authP;
		
	}
	
	
	@Bean
	public SecurityFilterChain internalSecurityFilerChain(HttpSecurity http) throws Exception {
		return http.httpBasic(Customizer.withDefaults()).csrf((csrf)->csrf.disable()).authorizeHttpRequests(
				(requests)->requests.requestMatchers("/start/welcome").permitAll().
				requestMatchers("/start/adduser").hasRole("admin").
				requestMatchers("/token/generate_token","/new/access_by_token").authenticated()
				)
				.authenticationProvider(authProvider()).
				sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
				addFilterAfter(new JwtGenerationFilter(),BasicAuthenticationFilter.class).
				addFilterBefore(new JwtValidatorFilter(), BasicAuthenticationFilter.class)
				
	                .
				build();
		
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
