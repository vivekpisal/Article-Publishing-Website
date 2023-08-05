package com.codergeek.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PublishedArticle{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
	@Size(min=5,message="Title should be greater than 5 character")
	protected String title;
	
	@Column(nullable = false,length = 100000)
	@Size(min = 1,message = "Article could not be blank")
	protected String content;
	
	@ManyToOne
	protected Users user;

	@Size(min = 1,message = "Please select a valid topic")
	protected String topic;
	
	
	public PublishedArticle(String content,String topic,Users user) {
		super();
		this.content = content;
		this.user = user;
		this.topic = topic;
	}

	public PublishedArticle() {}
	
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
	public int hashCode() {
		return Objects.hash(content, id, title, topic);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PublishedArticle other = (PublishedArticle) obj;
		return Objects.equals(content, other.content) && Objects.equals(id, other.id)
				&& Objects.equals(title, other.title) && Objects.equals(topic, other.topic);
	}

	@Override
	public String toString() {
		return "PublishedArticle [id=" + id + ", title=" + title + ", user=" + user + ", topic=" + topic + "]";
	}	
	
}
