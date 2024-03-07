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

import org.springframework.web.filter.OncePerRequestFilter;

public class JwtGenerationFilter extends OncePerRequestFilter {
	String secret="jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
	
	
	
	
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("generated-inside dointernalFilter");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication!=null) {
			SecretKey key= Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
			String jwt=Jwts.builder().setSubject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + 30000000))
                    .signWith(key).compact();
            response.setHeader("Authorization", jwt);
			System.out.println("genreated"+jwt);				
			
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	@Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
		String path=request.getServletPath();
		
		boolean t=path.equals("/new/access_by_token");
		System.out.println(path+"  "+t);
        return t;
    }
	
	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> authoritySet=new HashSet<>();
		for(GrantedAuthority authority:collection) {
			authoritySet.add(authority.getAuthority());
		}
		return String.join(",", authoritySet);
	}
	
	

}
