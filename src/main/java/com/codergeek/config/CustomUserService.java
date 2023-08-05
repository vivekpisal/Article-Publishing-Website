package com.codergeek.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codergeek.entities.Users;
import com.codergeek.repositories.UserRepo;

@Service
public class CustomUserService implements UserDetailsService{

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepo.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("UserName not found");
		}
		String userName = user.getEmail();
		String password = user.getPassword();
		String role = "ROLE_" + user.getRole();
		return new User(userName,password,List.of(new SimpleGrantedAuthority(role)));
	}

}
