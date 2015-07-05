package com.paymee.domain.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.paymee.domain.account.au.AuAccountNumber;
import com.paymee.domain.account.au.AuBsb;

public class Account {

	private AccountRegion region;
	private AuBsb bsb;
	private AuAccountNumber accountNumber;

	public Account(@JsonProperty("region")AccountRegion region, @JsonProperty("bsb")AuBsb bsb, @JsonProperty("accountNumber")AuAccountNumber accountNumber) {
		this.region = region;
		this.bsb = bsb;
		this.accountNumber = accountNumber;
	}

	public AuBsb getBsb() {
		return bsb;
	}

	public AuAccountNumber getAccountNumber() {
		return accountNumber;
	}

	public AccountRegion getRegion() {
		return AccountRegion.AU;
	}
}
