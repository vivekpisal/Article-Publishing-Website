package com.codergeek.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
public class JobApplication {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Email
	private String email;
	
	private String firstName;
	
	@Size(min = 10,max = 10,message = "Mobile Number Must be contain 10 numbers")
	private String mobileNumber;
	
	private String company;
	
	private String designation;

	public JobApplication() {
		super();
	}

	public JobApplication(Integer id, @Email String email, String mobileNumber,
			String company, String designation) {
		super();
		this.id = id;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.company = company;
		this.designation = designation;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Integer getId() {
		return id;
	}
	
	
}
