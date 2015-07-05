package com.paymee.domain.user;

import com.paymee.domain.identity.KnownIdentities;

/**
 * A user of the system
 */
public class User {

	private String username;
	private String passwordMd5;

	/* A user has a 1:1 relationship with an entity */
	private KnownIdentities knownIdentities;

}
