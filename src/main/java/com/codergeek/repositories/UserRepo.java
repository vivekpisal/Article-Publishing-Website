package com.codergeek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codergeek.entities.Users;

public interface UserRepo extends JpaRepository<Users, Long>{
	
	Users findByEmail(String email);
	
	Boolean existsByEmail(String email);
}
