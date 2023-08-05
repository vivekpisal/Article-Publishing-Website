package com.codergeek.repositories;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codergeek.entities.PublishedArticle;

@Repository
public interface PublishedArticleRepo extends CrudRepository<PublishedArticle, Long>{
	
	public Set<PublishedArticle> findByTitleContaining(String title);
} 
