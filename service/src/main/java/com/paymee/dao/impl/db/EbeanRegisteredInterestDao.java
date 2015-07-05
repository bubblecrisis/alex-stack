package com.paymee.dao.impl.db;


import com.avaje.ebean.EbeanServer;
import com.paymee.dao.registered_interest.RegisteredInterestDao;
import com.paymee.domain.identity.RegisteredInterest;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class EbeanRegisteredInterestDao implements RegisteredInterestDao {

	private final EbeanServer ebeanServer;

	@Inject
	public EbeanRegisteredInterestDao(EbeanServer ebeanServer) {
		this.ebeanServer = ebeanServer;
	}

	@Override
	public void registerInterest(RegisteredInterest address) {
		ebeanServer.save(address);
	}
}
