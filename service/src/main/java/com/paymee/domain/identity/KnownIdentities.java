package com.paymee.domain.identity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.paymee.domain.account.Account;
import com.paymee.domain.identity.Identity;
import jersey.repackaged.com.google.common.collect.Lists;

import java.util.Collection;

public class KnownIdentities {
	private Long id;
	private String name;
	private Collection<Identity> knownIdentities;
	private Account account;

	@JsonCreator
	public KnownIdentities(@JsonProperty("id")Long id, @JsonProperty("name")String name, @JsonProperty("knownIdentities")Collection<Identity> knownIdentities, @JsonProperty("account")Account account) {
		this.id = id;
		this.name = name;
		this.knownIdentities = knownIdentities;
		this.account = account;
	}

	public static Builder builder() {
		return new Builder();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Collection<Identity> getKnownIdentities() {
		return knownIdentities;
	}

	public Account getAccount() {
		return account;
	}

	public static class Builder {
		private Long id;
		private String name;
		private Collection<Identity> knownIdentities;
		private Account account;

		public Builder() {
			this.knownIdentities = Lists.newArrayList();
		}

		public Builder id(Long id) {
			this.id = id; return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder identity(Identity identity) {
			knownIdentities.add(identity);
			return this;
		}

		public Builder account(Account account) {
			this.account = account;
			return this;
		}

		public KnownIdentities build() {
			return new KnownIdentities(id, name, knownIdentities, account);
		}

	}
}
