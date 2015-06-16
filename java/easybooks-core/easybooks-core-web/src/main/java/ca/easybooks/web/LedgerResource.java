package ca.easybooks.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import ca.easybooks.data.transferobject.LedgerEntryInput;
import ca.easybooks.service.interfaces.HelloWorldEjb;
import ca.easybooks.service.interfaces.LedgerService;

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

    @GET
    @Path("/transactions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactions() {
        return Response.ok(ledgerService.getTransactions()).build();

    }

}
