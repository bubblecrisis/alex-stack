package com.paymee.service.resource;

import com.paymee.dao.registered_interest.RegisteredInterestDao;
import com.paymee.domain.Result;
import com.paymee.domain.identity.RegisteredInterest;
import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
@Path("registerinterest")
@Produces(MediaType.APPLICATION_JSON)
public class RegisteredInterestResource {

	@Autowired
	private RegisteredInterestDao dao;

	@POST
	public Response registerInterest(RegisteredInterest interest, @Context HttpServletRequest req) {
		try {
			if (!EmailValidator.getInstance().isValid(interest.getEmail())) {
				return Response.serverError().header("ERROR_FIELD", "email").build();
			} else {
				if (req == null) {
					interest.setIpAddress("--unknown--");
				} else {
					interest.setIpAddress(req.getRemoteAddr());
				}
				dao.registerInterest(interest);
				return Response.ok(Result.SUCCESS).build();
			}
		} catch (Exception e) {
			return Response.serverError().header("ERROR_LOGGED", "").build();
		}

	}

}

