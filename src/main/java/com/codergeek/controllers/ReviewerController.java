package com.codergeek.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.codergeek.entities.SubmittedArticle;
import com.codergeek.repositories.SubmittedArticleRepo;
import com.codergeek.services.SubmittedArticleService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ReviewerController {
	@Autowired
	private SubmittedArticleRepo submittedArticleRepo;
	
	@Autowired
	private SubmittedArticleService subService;
	
	@GetMapping("/reviewarticles")
	public String reviewArticle(ModelMap model,HttpServletRequest request) {
		List<SubmittedArticle> allArticles = subService.findByisPublishedFalse();
		model.put("allArticles",allArticles);
		model.put("url", request.getRequestURI());
		return "reviewarticle";
	}
	
	@GetMapping("/deletearticle/{id}")
	public String deleteArticle(@PathVariable("id") Long id) {
		submittedArticleRepo.deleteById(id);
		return "redirect:/reviewarticles";
	}
}
