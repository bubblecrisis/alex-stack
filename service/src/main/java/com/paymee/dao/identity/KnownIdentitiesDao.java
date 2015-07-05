package com.paymee.dao.identity;

import com.paymee.domain.identity.KnownIdentities;
import com.paymee.domain.identity.IdentityType;

import java.util.Optional;

public interface KnownIdentitiesDao {
	Optional<KnownIdentities> findByIdentity(IdentityType type, String identity);
	Optional<KnownIdentities> findByKnownIdentityId(Long id);
	KnownIdentities save(KnownIdentities entity);
	KnownIdentities update(KnownIdentities entity);
	void delete(Long id);
}
