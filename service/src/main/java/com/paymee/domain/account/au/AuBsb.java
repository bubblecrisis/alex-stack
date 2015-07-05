package com.paymee.domain.account.au;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuBsb {
	private int code;

	@JsonCreator
	public AuBsb(@JsonProperty("code")int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
