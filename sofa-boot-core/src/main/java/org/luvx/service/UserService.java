package org.luvx.service;

import javax.ws.rs.*;

@Path("/user")
@Consumes("application/json;charset=UTF-8")
@Produces("application/json;charset=UTF-8")
public interface UserService {
    @GET
    @Path("/getUser/{id}")
    String getUser(@PathParam("id") String id);
}
