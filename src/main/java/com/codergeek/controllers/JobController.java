package com.codergeek.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.codergeek.entities.Jobs;
import com.codergeek.entities.Profile;
import com.codergeek.entities.Users;
import com.codergeek.repositories.JobsRepo;
import com.codergeek.repositories.ProfileRepo;
import com.codergeek.repositories.UserRepo;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class JobController {
	
	@Autowired
	private ProfileRepo profileRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JobsRepo jobRepo;
	
	@GetMapping("/applyjob/{id}")
	public String applyJob(@PathVariable int id,ModelMap model) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Jobs job = jobRepo.findById(id).get();
		List<Profile> applications = job.getApplications();
		boolean  alreadyApplied = applications.stream().anyMatch(p -> 
			p.getEmail().equals(email) ? true:false);
		if(alreadyApplied == true) {
			model.put("alreadyApplied", true);
			return "redirect:/jobs";
		}
		model.put("id", id);
		return "applyjob";
	}
	
	@PostMapping("/applyjob/{id}")
	public String applyJob(@RequestParam("qualification") String qualification,@RequestParam("experience") Float experience,@RequestParam("resume") MultipartFile file,@PathVariable int id,ModelMap model) throws IOException {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Users user = userRepo.findByEmail(email);
		Jobs job = jobRepo.findById(id).get();
		Profile profile = new Profile();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		profile.setResumeName(fileName);
		profile.setExperience(experience);
		profile.setQualification(qualification);
		profile.setResume(file.getBytes());
		System.out.println(profile.getResume().getClass().getName());
		profile.setEmail(email);
		profile.setUser(user);
		profile.setJobs(job);
		job.addApplication(profile);
		profileRepo.save(profile);
		jobRepo.save(job);
		return "applyjob";
	}
	
	@GetMapping("/show-CV/{id}")
	public void showResume(@PathVariable int id,HttpServletResponse response) throws IOException {
		Profile profile = profileRepo.findById(id).get();
		response.setContentType("application/pdf");
		response.getOutputStream().write(profile.getResume());
		response.getOutputStream().close();
	}
	
	@GetMapping("/getjob")
	@ResponseBody
	public List<Jobs> job() {
		List<Jobs> findAll = jobRepo.findAll();
		return findAll;
	}
}
