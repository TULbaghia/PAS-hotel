package pl.lodz.p.pas.service;

import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.service.filters.userFilters.activateUserFilter.PatchActivateUserCheckBinding;
import pl.lodz.p.pas.service.filters.userFilters.addUserFilter.PostAddUserCheckBinding;
import pl.lodz.p.pas.service.filters.userFilters.updateUserFilter.PutUpdateUserCheckBinding;

import javax.annotation.security.RolesAllowed;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@ServletSecurity(@HttpConstraint(transportGuarantee =
        ServletSecurity.TransportGuarantee.CONFIDENTIAL))
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("guest")
@RolesAllowed({"Manager"})
public class GuestService extends UserAbstractService<Guest> {

    public GuestService() {
        super(new Guest());
    }

    @GET
    @Override
    public String getAllUsers() {
        return super.getAllUsers();
    }

    @Path("/{uuid}")
    @GET
    @Override
    public String getUser(@PathParam("uuid") String id, @Context SecurityContext securityContext)  {
        return super.getUser(id, securityContext);
    }

    @POST
    @PostAddUserCheckBinding
    public String addUser(@Valid Guest user) {
        return super.addUser(user);
    }

    @PUT
    @PutUpdateUserCheckBinding
    public String updateUser(@Valid Guest user) {
        return super.updateUser(user);
    }

    @PATCH
    @PatchActivateUserCheckBinding
    public String activateUser(Guest user) {
        return super.activateUser(user);
    }
}