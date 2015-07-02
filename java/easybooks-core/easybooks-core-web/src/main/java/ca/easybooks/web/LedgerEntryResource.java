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

import ca.easybooks.data.entity.LedgerEntry;
import ca.easybooks.data.transferobject.LedgerEntryInput;
import ca.easybooks.service.interfaces.LedgerEntryService;

@Path("ledgerentry")
@RequestScoped
public class LedgerEntryResource
{

    @Inject
    private LedgerEntryService ledgerEntryService;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createLedgerEntry(@MultipartForm final LedgerEntryInput ledgerEntryInput) {
        final LedgerEntry ledgerEntry = ledgerEntryService.createLedgerEntry(ledgerEntryInput);
        return Response.ok(ledgerEntry).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLedgerEntries() {
        return Response.ok(ledgerEntryService.getAllTransactions()).build();

    }

    @GET
    @Path("/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLedgerEntriesForYear(final int year) {
        return Response.ok(ledgerEntryService.getTransactionsForYear(year)).build();

    }
}
