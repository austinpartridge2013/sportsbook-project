package com.thefightpredictor.sportsbook.core.web;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.thefightpredictor.sportsbook.core.service.HelloWorldEjb;

@Path("blah")
public class App
{
    @Inject
    private HelloWorldEjb helloWorldEjb;

    @Path("blah")
    @GET
    public Response getHelloWorld()
    {
        return Response.ok(helloWorldEjb.getHelloWorld()).build();
    }
}
