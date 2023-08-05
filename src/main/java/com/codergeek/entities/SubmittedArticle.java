package com.codergeek.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SubmittedArticle {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
	@Size(min=5,message="Title should be greater than 5 character")
	protected String title;
	
	@Lob
	@Column(nullable = false,length = 100000)
	@Size(min = 1,message = "Article could not be blank")
	protected String content;
	
	@ManyToOne
	protected Users user;

	@Size(min = 1,message = "Please select a valid topic")
	protected String topic;
	
	private Boolean isPublished = false;
	
	public SubmittedArticle(String content,String topic, Users user) {
		super();
		this.content = content;
		this.user = user;
		this.topic = topic;
	}

	public SubmittedArticle() {
		
	}

	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Long getId() {
		return id;
	}
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "SubmittedArticle [id=" + id + ", content=" + content + ", user=" + user + "]";
	}
	
	
}
