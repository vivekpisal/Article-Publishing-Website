package com.codergeek.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codergeek.entities.PublishedArticle;
import com.codergeek.entities.SubmittedArticle;
import com.codergeek.repositories.PublishedArticleRepo;
import com.codergeek.repositories.SubmittedArticleRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PublishedArticleServices {
	
	@Autowired
	private PublishedArticleRepo repo;
	
	@Autowired
	private SubmittedArticleRepo submittedRepo;
	
	public PublishedArticle saveArticle(long id) {
		Optional<SubmittedArticle> articleOp = submittedRepo.findById(id);
		PublishedArticle publishedArticle = new PublishedArticle();
		SubmittedArticle article = articleOp.get();
		publishedArticle.setContent(article.getContent());
		publishedArticle.setTitle(article.getTitle());
		publishedArticle.setTopic(article.getTopic());
		publishedArticle.setUser(article.getUser());
		repo.save(publishedArticle);
		article.setIsPublished(true);
		return publishedArticle;
	}

	public PublishedArticle findById(Long id) {
		return repo.findById(id).orElse(null);
	}
}
