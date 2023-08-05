package com.codergeek.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Jobs {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	private String jobTitle;
	
	@NotBlank
	private String jobType;
	
	@NotBlank
	private String companyName;
	
	@NotBlank
	private String role;
	
	@NotBlank
	private String location;
	
	@NotNull
	private LocalDate lastDate;
	
	@NotNull
	private Integer salary;
	
	@NotBlank
	private String jobDescription;
	
	@ManyToOne
	private Users user;
	
	@NotNull
	@OneToMany(mappedBy = "jobs",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Profile> applications = new ArrayList<>();
	
	public Jobs() {
		super();
	}

	public Jobs(@NotBlank String companyName, @NotBlank String role, @NotBlank String location,
			@NotNull LocalDate lastDate, @NotNull Integer salary, @NotBlank String jobDescription) {
		super();
		this.companyName = companyName;
		this.role = role;
		this.location = location;
		this.lastDate = lastDate;
		this.salary = salary;
		this.jobDescription = jobDescription;
	}

	
	
	public List<Profile> getApplications() {
		return applications;
	}

	public void setApplications(List<Profile> applications) {
		this.applications = applications;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getLastDate() {
		return lastDate;
	}

	public void setLastDate(LocalDate lastDate) {
		this.lastDate = lastDate;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public void removeApplication(Profile application) {
		this.applications.remove(application);
	}

	public void addApplication(Profile application) {
		this.applications.add(application);
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Jobs [id=" + id + ", jobTitle=" + jobTitle + ", jobType=" + jobType + ", companyName=" + companyName
				+ ", role=" + role + ", location=" + location + ", lastDate=" + lastDate + ", salary=" + salary
				+ ", jobDescription=" + jobDescription + ", user=" + user + ", applications=" + applications + "]";
	}

	
	
	
}
