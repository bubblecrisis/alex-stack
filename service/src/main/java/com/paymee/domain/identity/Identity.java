package com.paymee.domain.identity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class Identity {
	Long id;
	private IdentityType type;
	private String identifier;
	private boolean isVerified;

	@JsonIgnore
	private transient String validationToken;


	@JsonCreator
	public Identity(@JsonProperty("id")Long id, @JsonProperty("type")IdentityType type, @JsonProperty("identifier")String identifier) {
		this.id = id;
		this.type = type;
		this.identifier = identifier;
		this.isVerified = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public IdentityType getType() {
		return type;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void createValidationToken() {
		this.validationToken= DigestUtils.md5Hex("" + new Random().nextLong());
	}

	@JsonIgnore
	public String getValidationToken() {
		return validationToken;
	}
}
