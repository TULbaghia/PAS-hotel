package pl.lodz.p.pas.service;

import pl.lodz.p.pas.model.user.Manager;
import pl.lodz.p.pas.service.filters.userFilters.activateUserFilter.PatchActivateUserCheckBinding;
import pl.lodz.p.pas.service.filters.userFilters.addUserFilter.PostAddUserCheckBinding;
import pl.lodz.p.pas.service.filters.userFilters.updateUserFilter.PutUpdateUserCheckBinding;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("manager")
public class ManagerService extends UserAbstractService<Manager>{

    public ManagerService() {
        super(new Manager());
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
        securityContext.isSecure();
        return super.getUser(id, securityContext);
    }

    @POST
    @PostAddUserCheckBinding
    public String addUser(@Valid Manager user) {
        return super.addUser(user);
    }

    @PUT
    @PutUpdateUserCheckBinding
    public String updateUser(@Valid Manager user) {
        return super.updateUser(user);
    }

    @PATCH
    @PatchActivateUserCheckBinding
    public String activateUser(Manager user) {
        return super.activateUser(user);
    }
}