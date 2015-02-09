package com.thefightpredictor.sportsbook.core.web;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.thefightpredictor.sportsbook.core.service.interfaces.IHelloWorldEjb;

@Path("blah")
public class App
{
    @Inject
    private IHelloWorldEjb helloWorldEjb;

    @Path("blah")
    @GET
    public Response getHelloWorld()
    {
        final String helloWorld = helloWorldEjb.getHelloWorld();
        return Response.ok(helloWorld).build();
    }
}
