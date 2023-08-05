package com.codergeek.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codergeek.entities.Jobs;
import com.codergeek.entities.Users;

public interface JobsRepo extends JpaRepository<Jobs, Integer>{

	List<Jobs> findByUser(Users user);
	
}
