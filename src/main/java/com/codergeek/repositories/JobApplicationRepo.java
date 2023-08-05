package com.codergeek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codergeek.entities.JobApplication;

public interface JobApplicationRepo extends JpaRepository<JobApplication, Integer>{
	
}
