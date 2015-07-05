package com.paymee.service.resource;

import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by alex on 28/03/2015.
 */
@Service
@Path("hello")
public class Hello {

    @GET
    public Response get() {
        return Response.ok("hello").build();
    }
}
