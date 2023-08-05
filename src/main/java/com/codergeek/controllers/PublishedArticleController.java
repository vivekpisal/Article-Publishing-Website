package com.codergeek.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.codergeek.entities.PublishedArticle;
import com.codergeek.entities.SubmittedArticle;
import com.codergeek.repositories.PublishedArticleRepo;
import com.codergeek.services.PublishedArticleServices;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/publishedarticle")
public class PublishedArticleController {
	
	@Autowired
	private PublishedArticleServices pubArticleServices;
	
	@Autowired
	private PublishedArticleRepo pubArcticlerepo;
	
	
	@GetMapping("/{id}")
	public String publishedArticle(@PathVariable("id") Long id,HttpServletRequest request,ModelMap model) {
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
		model.put("url",request.getRequestURI());
		model.put("article", pubArticleServices.findById(id));
		return "allArticles";
	}
	
	@PostMapping("/{id}")
	public RedirectView publishedArticle(@PathVariable("id") Long id,@ModelAttribute("article") SubmittedArticle article
			,ModelMap model,HttpServletRequest request,RedirectAttributes redir) {
		PublishedArticle pubArticle = pubArticleServices.saveArticle(id);
		model.put("url",request.getRequestURI());
		RedirectView view = new RedirectView("/reviewarticles",true);
		redir.addFlashAttribute("saved", "true");
		return view;
	}
}
