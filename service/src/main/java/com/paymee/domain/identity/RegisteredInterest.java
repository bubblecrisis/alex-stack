package com.paymee.domain.identity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="registered_interest")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisteredInterest {
	@Id
	private Long id;
	private String email;
	private String ipAddress;
	private Date registered;

	public RegisteredInterest() {

	}

	@JsonCreator
	public RegisteredInterest(@JsonProperty("email") String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	@PrePersist
	public void createdAt() {
		this.registered = new Date();
	}

	public Date getRegistered() {
		return registered;
	}

	public String getEmail() {
		return email;
	}
}
