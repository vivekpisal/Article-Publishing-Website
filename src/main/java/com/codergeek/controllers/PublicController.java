package com.codergeek.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codergeek.basicservices.EmailDetails;
import com.codergeek.basicservices.EmailService;
import com.codergeek.basicservices.OtpGenerator;
import com.codergeek.entities.JobApplication;
import com.codergeek.entities.Jobs;
import com.codergeek.entities.OTP;
import com.codergeek.entities.PublishedArticle;
import com.codergeek.entities.Users;
import com.codergeek.repositories.JobApplicationRepo;
import com.codergeek.repositories.JobsRepo;
import com.codergeek.repositories.OTPRepo;
import com.codergeek.repositories.PublishedArticleRepo;
import com.codergeek.repositories.UserRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class PublicController {
	
	@Autowired
	private PublishedArticleRepo pubArcticlerepo;
	
	@Autowired
	private JobApplicationRepo applicationRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private EmailService email;
	
	@Autowired
	private OtpGenerator getOtp;
	
	@Autowired
	private OTPRepo otpRepo;
	
	@Autowired
	private JobsRepo jobRepo;
	
	
	
	@PostMapping("/forgotpassword")
	public String sendMail(HttpServletRequest request,ModelMap model){
		int otp = getOtp.generateOTP();
		String message = "OTP to reset password " + otp;
		String emailId = request.getParameter("email");
		Users user = userRepo.findByEmail(emailId);
		if(user == null) {
			model.put("found",false);
			return "forgotpassword";
		}
		EmailDetails passwordReset = new EmailDetails(emailId, message,
		  "Forgot Password OTP"); 
		email.sendMail(passwordReset);
		OTP userotp;
		if(otpRepo.findByEmail(emailId) == null) {
			userotp = new OTP(emailId,otp);
		}
		else {
			userotp = otpRepo.findByEmail(emailId);
			userotp.setOtp(otp);
		}
		otpRepo.save(userotp);
		model.put("email", emailId);
		return "resetpassword";
	}
	
	
	@GetMapping("/forgotpassword")
	public String sendMail(){
		return "forgotpassword";
	}
	
	
	@PostMapping("/resetpassword")
	public String resetPassword(HttpServletRequest request,ModelMap model) {
		String email = request.getParameter("email");
		model.put("email", email);
		int userEnteredOtp = Integer.valueOf(request.getParameter("otp"));
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmpassword");
		if(email != null) {
			OTP otp = otpRepo.findByEmail(email);
			int originalOtp = otp.getOtp();
			if(userEnteredOtp != originalOtp) {
				model.put("otpmatched",false);
				return "resetpassword";
			}else {
				if(password == null) {
					model.put("passwordError","Password can't be blank");
					return "resetpassword";
				}else if(!password.equals(confirmPassword)) {
					model.put("passwordError","Password must be match");
					return "resetpassword";
				}
			}
		}
		Users user = userRepo.findByEmail(email);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(confirmPassword));
		userRepo.save(user);
		return "redirect:/signin";
	}
	
	@GetMapping("/jobs")
	public String jobs(HttpServletRequest request,ModelMap model) {
		List<Jobs> Jobs = jobRepo.findAll();
		List<Jobs> allJobs = Jobs.stream().filter(job -> job.getJobType().equals("Full-Time")).collect(Collectors.toList());
		List<Jobs> allInternships = Jobs.stream().filter(job -> job.getJobType().equals("Internship")).collect(Collectors.toList());
		model.put("allJobs", allJobs);
		model.put("allInternships", allInternships);
		model.put("url",request.getRequestURI());
		return "alljobs";
	}
	
	
	@GetMapping("/jobapp")
	public String jobapplication(ModelMap model) {
		model.put("application", new JobApplication());
		return "jobapplication";
	}
	
	@PostMapping("/jobapp")
	public String jobapplicatio(@Valid @ModelAttribute("application") JobApplication job,BindingResult result,ModelMap model) {
		if(result.hasErrors()) {
			model.put("application", job);
			return "jobapplication";
		}
		boolean userAlreadyPresent = userRepo.existsByEmail(job.getEmail());
		if(userAlreadyPresent) {
			model.put("userAlreadyPresent", true);
			return "jobapplication";
		}
		applicationRepo.save(job);
		model.put("saved",true);
		return "jobapplication";
	}
	
	
	@GetMapping("/")
	public String allArticles(HttpServletRequest request,ModelMap model) {
		model.put("url",request.getRequestURI());
		try {
			List<PublishedArticle> pubArticle = (List<PublishedArticle>) pubArcticlerepo.findAll();
			model.put("allArticles",pubArticle);
			PublishedArticle article = pubArticle.stream().findAny().get();
			model.put("article", article);
			model.put("articleFound", true);
		}
		catch(Exception e) {
			model.put("articleFound", false);
		}
		return "allArticles";
	}
	
	@PostMapping("/search")
	public String searchArticles(HttpServletRequest request,ModelMap model) {
		String search = request.getParameter("search");
		Set<PublishedArticle> articles = pubArcticlerepo.findByTitleContaining(search);
		model.put("allArticles", articles);
		return "searchResult";
	}
	
	
	@GetMapping("/article/{id}")
	public String allArticles(@PathVariable long id,HttpServletRequest request,ModelMap model) {
		model.put("url",request.getRequestURI());
		List<PublishedArticle> pubArticle = (List<PublishedArticle>) pubArcticlerepo.findAll();
		Optional<PublishedArticle> article = pubArcticlerepo.findById(id);
		model.put("article",article.get());
		model.put("allArticles",pubArticle);
		return "allArticles";
	}
	
	@GetMapping("/signin")
	public String login(HttpServletRequest request,ModelMap model) {
		model.put("url", request.getRequestURI());
		return "login";
	}
	
	@GetMapping("/signup")
	public String register(ModelMap model) {
		model.put("user", new Users());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String register(@Valid @ModelAttribute("user") Users user,BindingResult result,HttpServletRequest request,ModelMap model) {
		if(result.hasErrors() || !user.getPassword().equals(request.getParameter("password1"))) {
			System.out.println(result.hasErrors() + " " + result.getErrorCount() + " " + result);
			System.out.println(user.getPassword());
			System.out.println(request.getParameter("password1"));
			if(!user.getPassword().equals(request.getParameter("password1"))) {
				model.put("passwordNotMatch", "true");
			}
			model.put("user",user);
			return "signup";
		}
		if(userRepo.existsByEmail(user.getEmail())) {
			model.put("userExists", "true");
			return "signup";
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		userRepo.save(user);
		model.put("userCreated", "true");
		return "signup";
	}
	
	
	@GetMapping("/changepassword")
	public String changePassword(ModelMap model,HttpServletRequest request) {
		model.put("url", request.getRequestURI());
		return "changepassword";
	}
	
	@PostMapping("/changepassword")
	public String changePassword(@RequestParam("newPassword1") String confirmPassword,HttpServletRequest request,ModelMap model) {
		model.put("url", request.getRequestURI());
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Users user = userRepo.findByEmail(email);
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String password = user.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String oldEnCryptedPassword = encoder.encode(oldPassword);
		System.out.println(confirmPassword);
		if(encoder.matches(oldPassword,password)) {
			if(newPassword.equals(confirmPassword)) {
				user.setPassword(encoder.encode(newPassword));
				userRepo.save(user);
				model.put("passwordChanged",true);
			}else {
				model.put("passwordMatch",true);
			}
		}else {
			model.put("oldPassword", true);
		}
		return "changepassword";
	}
}
