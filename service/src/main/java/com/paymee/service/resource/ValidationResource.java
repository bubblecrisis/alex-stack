package com.paymee.service.resource;

import com.paymee.dao.identity.KnownIdentitiesDao;
import com.paymee.domain.identity.Identity;
import com.paymee.domain.identity.IdentityType;
import com.paymee.domain.identity.KnownIdentities;
import com.paymee.service.email.EmailValidationService;
import jersey.repackaged.com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Service
@Path("validation")
@Produces(MediaType.APPLICATION_JSON)
public class ValidationResource {

	@Autowired
	private KnownIdentitiesDao dao;

	@GET
	@Path("/{id}/identity/{identityId}/verify")
	public Response validateIdentity(@PathParam("id")Long id, @PathParam("identityId")Long identityId, @QueryParam("validationToken")String token) {
		Optional<KnownIdentities> oCurrent = dao.findByKnownIdentityId(id);
		if (!oCurrent.isPresent()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		boolean success = false;
		KnownIdentities current = oCurrent.get();
		for (Identity identity : current.getKnownIdentities()) {
			if (token.equals(identity.getValidationToken())) {
				identity.setVerified(true);
				success = true;
			}
		}
		if (success) {
			dao.save(current);
			return Response.ok().build(); // TODO - redirect to user's home page
		} else {
			return Response.serverError().build();
		}

	}

}

