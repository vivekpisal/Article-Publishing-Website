package com.codergeek.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codergeek.entities.SubmittedArticle;

@Repository
public interface SubmittedArticleRepo extends JpaRepository<SubmittedArticle, Long>{
	
	public List<SubmittedArticle> findByisPublishedFalse();
	
	public List<SubmittedArticle> findByisPublishedTrue();
}
