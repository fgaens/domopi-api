package be.codesolutions.domopi.api.resource;

import be.codesolutions.domopi.api.dto.ValveUpdateRequest;
import be.codesolutions.domopi.application.service.ValveService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/valves")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ValveResource {

    private final ValveService valveService;

    public ValveResource(ValveService valveService) {
        this.valveService = valveService;
    }

    @PUT
    @Path("/{valveId}")
    @RolesAllowed({"Admin", "User"})
    public Response updateValve(@PathParam("valveId") String valveId, ValveUpdateRequest request) {
        valveService.switchValve(valveId, request.state());
        return Response.ok().build();
    }
}