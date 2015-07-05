package com.paymee.domain.account.au;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

public class AuAccountNumber {
	private BigInteger account;

	@JsonCreator
	public AuAccountNumber(@JsonProperty("account")BigInteger account) {
		this.account = account;
	}

	public BigInteger getAccount() {
		return account;
	}
}
