package com.thefightpredictor.sportsbook.core.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("blah")
public class App 
{
	@Path("blah")
	@GET
    public Response getHelloWorld()
    {
        return Response.ok("Hello world").build();
    }
}
