package com.example.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

public class JwtGenerationFilter {
	String secret="jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
	
	
	public String generateToken(Authentication authentication)
			 {
		// TODO Auto-generated method stub
		String jwt="";
		if(authentication!=null) {
			SecretKey key= Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
			jwt=Jwts.builder().setSubject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + 30000000))
                    .signWith(key).compact();
            
			System.out.println(jwt);				
			
		}
		
		return jwt;
		
	}
	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> authoritySet=new HashSet<>();
		for(GrantedAuthority authority:collection) {
			authoritySet.add(authority.getAuthority());
		}
		return String.join(",", authoritySet);
	}
	
	

}
