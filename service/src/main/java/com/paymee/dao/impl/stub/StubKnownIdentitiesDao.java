package com.paymee.dao.impl.stub;

import com.paymee.dao.identity.KnownIdentitiesDao;
import com.paymee.domain.account.Account;
import com.paymee.domain.account.AccountRegion;
import com.paymee.domain.account.au.AuAccountNumber;
import com.paymee.domain.account.au.AuBsb;
import com.paymee.domain.identity.Identity;
import com.paymee.domain.identity.IdentityType;
import com.paymee.domain.identity.KnownIdentities;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class StubKnownIdentitiesDao implements KnownIdentitiesDao {

	private AtomicLong aggregateIdGenerator = new AtomicLong(2);
	private AtomicLong identityIdGenerator = new AtomicLong(2);

	private Map<Long, KnownIdentities> aggregateMap = new ConcurrentHashMap<>();
	{
		aggregateMap.put(1L, createDefault());
	}

	private static KnownIdentities createDefault() {
		Identity emailIdentity = new Identity(1L, IdentityType.EMAIL, "hong.yew@gmail.com");
		emailIdentity.createValidationToken();
		emailIdentity.setVerified(true);
		Identity twitterIdentity = new Identity(2L, IdentityType.PHONE, "0406 000 000");
		twitterIdentity.setVerified(false);
		return KnownIdentities.builder()
				.id(1L)
				.name("Hong Yew")
				.identity(emailIdentity)
				.identity(twitterIdentity)
				.account(new Account(AccountRegion.AU, new AuBsb(123456), new AuAccountNumber(new BigInteger("1234567890"))))
				.build();
	}


	@Override
	public Optional<KnownIdentities> findByIdentity(IdentityType type, String identityValue) {
		for (Map.Entry<Long, KnownIdentities> aggregate : aggregateMap.entrySet()) {
			for (Identity identity : aggregate.getValue().getKnownIdentities()) {
				if (identity.getType().equals(type) && identity.getIdentifier().equals(identityValue)) {
					return Optional.of(aggregate.getValue());
				}
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<KnownIdentities> findByKnownIdentityId(Long id) {
		for (Map.Entry<Long, KnownIdentities> aggregate : aggregateMap.entrySet()) {
			if (id.equals(aggregate.getKey())) {
				return Optional.of(aggregate.getValue());
			}
		}
		return Optional.empty();
	}

	@Override
	public KnownIdentities save(KnownIdentities entity) {
		if (entity.getId() == null) {
			entity.setId(aggregateIdGenerator.incrementAndGet());
		}
		for (Identity identity : entity.getKnownIdentities()) {
			if (identity.getId() == null) {
				identity.setId(identityIdGenerator.incrementAndGet());
			}
		}
		aggregateMap.put(entity.getId(), entity);
		return entity;
	}

	@Override
	public KnownIdentities update(KnownIdentities entity) {
		return save(entity);
	}

	@Override
	public void delete(Long id) {
		aggregateMap.remove(id);
	}
}
