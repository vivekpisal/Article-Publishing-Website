package com.codergeek.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codergeek.entities.JobApplication;
import com.codergeek.repositories.JobApplicationRepo;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ReviewApplicationController {
	
	@Autowired
	private JobApplicationRepo applicationRepo;
	
	@RequestMapping("/reviewapplications")
	public String reviewapplications(ModelMap model,HttpServletRequest request) {
		List<JobApplication> allApplications = applicationRepo.findAll();
		model.put("url", request.getRequestURI());
		model.put("jobApplications", allApplications);
		return "reviewjobs";
	}
}
