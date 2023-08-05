package com.codergeek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codergeek.entities.OTP;

@Repository
public interface OTPRepo extends JpaRepository<OTP, Integer>{
	
	OTP findByEmail(String email);
	
}
