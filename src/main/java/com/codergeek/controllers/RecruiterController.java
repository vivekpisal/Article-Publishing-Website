package com.codergeek.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codergeek.entities.Jobs;
import com.codergeek.entities.Profile;
import com.codergeek.entities.Users;
import com.codergeek.repositories.JobsRepo;
import com.codergeek.repositories.UserRepo;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class RecruiterController {
	
	@Autowired
	private JobsRepo jobRepo;

	@Autowired
	private UserRepo userRepo;
	
	@RequestMapping("/applications")
	public String jobApplications(HttpServletRequest request,ModelMap model) {
		String url = request.getRequestURI();
		model.put("url", url);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Users user = userRepo.findByEmail(email);
		List<Jobs> allJobs = jobRepo.findByUser(user);
		model.put("userId", user.getId());
		model.put("jobs", allJobs);
		return "jobApplications";
	}
	
	
	@GetMapping("/view-applicants/{id}")
	public String viewApplicants(@PathVariable int id,HttpServletRequest request,ModelMap model,Principal principle) {
		String email = principle.getName();
		Users user = userRepo.findByEmail(email);
		List<Jobs> jobs = user.getJobs();
		try {
			Jobs job = jobs.stream().filter(j -> j.getId() == id).findFirst().get();
			List<Profile> applications = job.getApplications();
			model.put("applications", applications);
		}
		catch(Exception e) {
			model.put("found", false);
		}
		return "viewapplicants";
	}
	
	
	@GetMapping("/createjob")
	public String createJob(HttpServletRequest request,ModelMap model) {
		String url = request.getRequestURI();
		Jobs job = new Jobs();
		model.put("url", url);
		model.put("job", job);
		return "createjob";
	}
	
	@PostMapping("/createjob")
	public String createJob(@ModelAttribute("job") Jobs job,HttpServletRequest request,ModelMap model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Users user = userRepo.findByEmail(email);
		job.setUser(user);
		jobRepo.save(job);
		model.put("createdJob", true);
		return "createjob";
	}
}
