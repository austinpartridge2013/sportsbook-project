package ca.easybooks.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import ca.easybooks.service.interfaces.DataPresentationService;

@Path("data")
@RequestScoped
public class LedgerDataResource
{
    @Inject
    private DataPresentationService dataPresentationService;

    @GET
    @Path("/incomestatement")
    @Produces("application/vnd.ms-excel")
    public Response getIncomeStatement() {
        final ResponseBuilder response = Response.ok(dataPresentationService.getAllTransactionsInExcelFormat());
        response.header("Content-Disposition",
                "attachment; filename=alltime_income_statement.xls");
        return response.build();
    }

    @GET
    @Path("/incomestatement/{year}")
    @Produces("application/vnd.ms-excel")
    public Response getIncomeStatement(@PathParam("year") final int year) {
        final ResponseBuilder response = Response.ok(dataPresentationService.getTransactionsInExcelFormat(year));
        response.header("Content-Disposition",
                "attachment; filename=" + year + "_income_statement.xls");
        return response.build();
    }

    @GET
    @Path("/zip")
    @Produces("application/zip")
    public Response getIncomeStatementWithAllDocumentation() {
        final ResponseBuilder response = Response.ok(dataPresentationService.getAllTransactionsIncludingDocumentationInZipFormat());
        response.header("Content-Disposition",
                "attachment; filename=new-zip-file.zip");
        return response.build();
    }

    @GET
    @Path("/zip/{year}")
    @Produces("application/zip")
    public Response getIncomeStatementForYearWithAllDocumentation(@PathParam("year") final int year) {
        final ResponseBuilder response = Response.ok(dataPresentationService.getTransactionsIncludingDocumentationInZipFormat(year));
        response.header("Content-Disposition",
                "attachment; filename=new-zip-file.zip");
        return response.build();
    }
}
