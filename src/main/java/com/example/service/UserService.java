package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repo.UserRepo;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	UserRepo repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User u=repo.findByName(username);
		if(u!=null) {
			return new org.springframework.security.core.userdetails.User(u.getName(),u.getPassword(),true,true,true,true,u.getAuthorities());
		}
		
		return null;
	}

}
