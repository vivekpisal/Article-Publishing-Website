package com.codergeek.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codergeek.basicservices.EmailDetails;
import com.codergeek.basicservices.EmailService;
import com.codergeek.basicservices.PwdGenerator;
import com.codergeek.entities.JobApplication;
import com.codergeek.entities.Users;
import com.codergeek.repositories.JobApplicationRepo;
import com.codergeek.repositories.UserRepo;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminController {
	@Autowired
	private EmailService emailService;
	
	@Autowired 
	private PwdGenerator pwdGenerator;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JobApplicationRepo jApplicationRepo;
	
	
	@RequestMapping("/createuser/{id}")
	public String createUser(@PathVariable int id) {
		Optional<JobApplication> jobApplication = jApplicationRepo.findById(id);
		JobApplication jobApp = jobApplication.get();
		String password = pwdGenerator.getSecurePassword();
		Users user = new Users(jobApp.getEmail(),"recruiter",jobApp.getFirstName());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(password));
		userRepo.save(user);
		String message = "Your Job Application Request Accepted Successfully! " + password;
		EmailDetails email = new EmailDetails(user.getEmail(),message,"Account Created Successfully");
		emailService.sendMail(email);
		jApplicationRepo.delete(jobApp);
		return "redirect:/reviewapplications";
	}
	
	@RequestMapping("/deleteuser/{id}")
	public String deleteUser(@PathVariable int id,HttpServletRequest request) {
		jApplicationRepo.deleteById(id);
		return "redirect:/reviewapplications";
	}
}
