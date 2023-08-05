package com.codergeek.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.codergeek.entities.SubmittedArticle;
import com.codergeek.entities.Users;
import com.codergeek.repositories.SubmittedArticleRepo;
import com.codergeek.repositories.UserRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class SubmitArticleController {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private SubmittedArticleRepo SubmittedArticleRepo;
	
	@GetMapping("/submitarticle")
	public String submitArticle(HttpServletRequest request,ModelMap model) {
		model.put("url",request.getRequestURI());
		model.put("article", new SubmittedArticle());
		return "submitarticle";
	}
	
	@GetMapping("/submitarticle/{id}")
	public String publishedArticle(@PathVariable("id") Long id,HttpServletRequest request,ModelMap model) {
		model.put("url",request.getRequestURI());
		model.put("article", SubmittedArticleRepo.findById(id).get());
		return "publishedarticle";
	}
	
	@PostMapping("/submitarticle")
	public String submitArticle(HttpServletRequest request,ModelMap model,@Valid @ModelAttribute("article") SubmittedArticle article,BindingResult result) {
		model.put("url",request.getRequestURI());
		if(result.hasErrors()) {
			return "submitarticle";
		}
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Users user = userRepo.findByEmail(name);
		article.setUser(user);
		SubmittedArticleRepo.save(article);
		return "submitarticle";
	}
}
