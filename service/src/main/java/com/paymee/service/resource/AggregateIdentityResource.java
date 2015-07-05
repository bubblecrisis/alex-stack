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
@Path("aggregate")
@Produces(MediaType.APPLICATION_JSON)
public class AggregateIdentityResource {

	@Autowired
	private KnownIdentitiesDao dao;

	@Autowired
	private EmailValidationService emailValidationService;

	@GET
	@Path("/{id}")
	public Response get(@PathParam("id")Long id) {
		Optional<KnownIdentities> ids = dao.findByKnownIdentityId(id);
		if (ids.isPresent()) {
			return Response.ok(ids.get()).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@GET
	public Response get(@QueryParam("idType")IdentityType idType, @QueryParam("id")String id) {
		Optional<KnownIdentities> ids = dao.findByIdentity(idType, id);
		if (ids.isPresent()) {
			return Response.ok(ids.get()).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}


	@POST
	public Response create(KnownIdentities identities) {
		KnownIdentities saved = dao.save(identities);
		return Response.ok(saved.getId()).build();
	}

	@POST
	@Path("/{id}/identity")
	public Response addIdentity(@PathParam("id")Long id, Identity identity) {
		Optional<KnownIdentities> current = dao.findByKnownIdentityId(id);
		if (current.isPresent()) {
			KnownIdentities cur = current.get();
			if (IdentityType.EMAIL.equals(identity.getType())) {
				identity.createValidationToken();
				emailValidationService.sendValidationEmail(id, identity);
			}
			cur.getKnownIdentities().add(identity);
			dao.save(cur);
			return Response.ok(identity).build();
		}

		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@PUT
	@Path("{id}")
	public Response update(@PathParam("id")Long id, KnownIdentities identities) {
		Preconditions.checkArgument(id.equals(identities.getId()));
		Optional<KnownIdentities> oCurrent = dao.findByKnownIdentityId(id);
		if (oCurrent.isPresent()) {
			dao.delete(id);
			KnownIdentities newIdentities = dao.save(identities);
			return Response.ok(newIdentities.getId()).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id")Long id) {
		dao.delete(id);
		return Response.ok(id).build();
	}

}

