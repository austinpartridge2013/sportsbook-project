package com.thefightpredictor.sportsbook.core.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thefightpredictor.sportsbook.core.data.transferobject.RegistrationAttempt;
import com.thefightpredictor.sportsbook.core.data.transferobject.RegistrationResponse;
import com.thefightpredictor.sportsbook.core.service.interfaces.HelloWorldEjb;
import com.thefightpredictor.sportsbook.core.service.interfaces.VehicleRegistrationService;

@Path("register")
@RequestScoped
public class RegistrationResource
{
    @Inject
    private HelloWorldEjb helloWorldEjb;

    @Inject
    private VehicleRegistrationService vehicleRegistrationService;

    @GET
    public Response getHelloWorld()
    {
        final String helloWorld = helloWorldEjb == null ? "Empty" : helloWorldEjb.getHelloWorld();
        return Response.ok(helloWorld).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerVehicle(final RegistrationAttempt registrationAttempt) {
        final RegistrationResponse registrationResponse = vehicleRegistrationService.registerVehicle(registrationAttempt);
        return Response.ok(registrationResponse).build();
    }

}
