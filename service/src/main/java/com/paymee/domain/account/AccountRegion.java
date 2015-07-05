package com.paymee.domain.account;

public enum AccountRegion {
	AU(Country.AU, AccountFormat.AU_BSB_ACCOUNT),
	NZ(Country.NZ, AccountFormat.NZ_BRANCH_ACCOUNT);

	private Country country;
	private AccountFormat accountFormat;

	AccountRegion(Country country, AccountFormat accountFormat) {
		this.country = country;
		this.accountFormat = accountFormat;
	}
}
