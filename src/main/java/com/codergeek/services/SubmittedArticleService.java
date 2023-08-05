package com.codergeek.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codergeek.entities.SubmittedArticle;
import com.codergeek.repositories.SubmittedArticleRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SubmittedArticleService {
	
	@Autowired
	private SubmittedArticleRepo repo;
	
	public SubmittedArticle findById(Long id) {
		return repo.findById(id).get();
	}
	
	public List<SubmittedArticle> findByisPublishedFalse(){
		return repo.findByisPublishedFalse();
	}
	
	public List<SubmittedArticle> findByisPublishedTrue(){
		return repo.findByisPublishedFalse();
	}
}
