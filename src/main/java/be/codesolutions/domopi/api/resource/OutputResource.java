package be.codesolutions.domopi.api.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/api/outputs")
@Produces("application/json")
@Consumes("application/json")
public class OutputResource {

    // TODO FGA: implement this endpoint
    @GET
    public Response getConfiguration() {
        return Response.ok().build();
    }
}