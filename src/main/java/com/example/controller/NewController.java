package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/new")
public class NewController {
	
	@GetMapping("/access_by_token")
	public String getInside() {
		return "logged in using token";
	}
	
	

}
