package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.filter.JwtGenerationFilter;
import com.example.model.User;
import com.example.repo.UserRepo;

@Component
@RestController
@RequestMapping("/start")
public class BaseController {
	JwtGenerationFilter generate=new JwtGenerationFilter();
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to my app";
		
	}
	@GetMapping("/index")
	public String getToIndex() {
		return "Index is accessible";
		
	}
	
	@PostMapping("/adduser")
	public String addUser(@RequestBody User user) {
		
		String pass=user.getPassword();
		user.setPassword(encoder.encode(pass));
		repo.save(user);
		return "User Saved";
		
	}
	
	@GetMapping("/block")
	public String block() {
		
		return "Only authenticated person can access this url";
	}
	
	@GetMapping("/spring")
	public String insideSpring() {
		Authentication  authentication=SecurityContextHolder.getContext().getAuthentication();
		String jwt=generate.generateToken(authentication);
		return jwt;
	}

}
