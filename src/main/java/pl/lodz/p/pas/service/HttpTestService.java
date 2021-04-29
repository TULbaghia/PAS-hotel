package pl.lodz.p.pas.service;

import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Path("/httptest")
public class HttpTestService {

    @GET
    public String getSelf(@Context SecurityContext securityContext) {
        return new JSONObject()
                .put("info", "Witaj https")
                .toString();
    }
}
