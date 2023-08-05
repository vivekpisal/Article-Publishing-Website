package com.codergeek.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotBlank
	@Size(min=5)
	private String name;

	@Email
	@NotBlank
	@Column(name = "email", unique = true)
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private String role = "user";

	@OneToMany(mappedBy = "user")
	List<SubmittedArticle> submittedArticle = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	List<PublishedArticle> publishedArticle = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	List<Jobs> jobs = new ArrayList<>();
	
	@JsonBackReference
	@OneToMany(mappedBy = "user")
	List<Profile> profiles = new ArrayList<>();
	

	public Users(String email, String role) {
		super();
		this.email = email;
		this.role = role;
	}
	
	public Users(String email, String role,String name) {
		super();
		this.email = email;
		this.role = role;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void removeProfile(Profile profile) {
		this.profiles.remove(profile);
	}

	public void addProfile(Profile profile) {
		this.profiles.add(profile);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public void removeJob(Jobs job) {
		this.jobs.remove(job);
	}

	public List<Jobs> getJobs() {
		return jobs;
	}

	public void addJob(Jobs job) {
		this.jobs.add(job);
	}

	public List<SubmittedArticle> getSubmittedArticle() {
		return submittedArticle;
	}

	public void addSubmittedArticle(SubmittedArticle submittedArticle) {
		this.submittedArticle.add(submittedArticle);
	}

	public List<PublishedArticle> getPublishedArticle() {
		return publishedArticle;
	}

	public void setPublishedArticle(PublishedArticle publishedArticle) {
		this.publishedArticle.add(publishedArticle);
	}

	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Users() {

	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", email=" + email + ", role=" + role + "]";
	}

}
