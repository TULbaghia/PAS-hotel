package pl.lodz.p.pas.service;

import lombok.Getter;
import org.json.JSONObject;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.service.dto.Mapper;
import pl.lodz.p.pas.service.views.Views;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("self")
@PermitAll
@ServletSecurity(@HttpConstraint(transportGuarantee =
        ServletSecurity.TransportGuarantee.CONFIDENTIAL))
public class UserSelfService {

    @Inject @Getter
    private UserManager userManager;

    @GET
    public String getSelf(@Context SecurityContext securityContext)  {
        String role;
        if(securityContext.isUserInRole("Admin")) {
            role = "Admin";
        } else if(securityContext.isUserInRole("Manager")) {
            role = "Manager";
        } else {
            role = "Guest";
        }
        User user = userManager.get(securityContext.getUserPrincipal().getName());
        String str = new Mapper().writeAsString(Views.Public.class, user);
        JSONObject jsonObject = new JSONObject(str);
        jsonObject.put("group", role);

        return jsonObject.toString();
    }
}
