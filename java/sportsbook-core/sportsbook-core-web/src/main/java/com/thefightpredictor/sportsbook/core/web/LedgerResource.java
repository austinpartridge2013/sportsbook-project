package com.thefightpredictor.sportsbook.core.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.thefightpredictor.sportsbook.core.data.transferobject.LedgerEntryInput;
import com.thefightpredictor.sportsbook.core.service.interfaces.HelloWorldEjb;
import com.thefightpredictor.sportsbook.core.service.interfaces.LedgerService;

@Path("register")
@RequestScoped
public class LedgerResource
{
    @Inject
    private HelloWorldEjb helloWorldEjb;

    @Inject
    private LedgerService ledgerService;

    @GET
    public Response getHelloWorld()
    {
        final String helloWorld = helloWorldEjb == null ? "Empty" : helloWorldEjb.getHelloWorld();
        return Response.ok(helloWorld).build();
    }

    @POST
    @Path("/upload-file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response registerVehicle(@MultipartForm final LedgerEntryInput registrationAttempt) {
        ledgerService.uploadFile(registrationAttempt);
        return Response.ok().build();
    }

}
