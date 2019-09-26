package org.acme.legume.resource;

import org.acme.legume.data.LegumeItem;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/legumes")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface LegumeApi {

    @POST
    @Path("/init")
    @Operation(
            operationId = "ProvisionLegumes",
            summary = "Add default legumes to the Database"
    )
    @APIResponse(
            responseCode = "201",
            description = "Default legumes created"
    )
    @APIResponse(
            name = "notFound",
            responseCode = "404",
            description = "Legume provision not found"
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error"
    )
    Response provision();

    @Operation(
            operationId = "ListLegumes",
            summary = "List all legumes"
    )
    @APIResponse(
            responseCode = "200",
            description = "The List with all legumes"
    )
    @APIResponse(
            name = "notFound",
            responseCode = "404",
            description = "Legume list not found"
    )
    @APIResponse(
            name = "internalError",
            responseCode = "500",
            description = "Internal Server Error"
    )
    @GET
    List<LegumeItem> list();
}
