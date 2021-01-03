package pl.lodz.p.pas.service;

import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.user.Guest;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("1/guest")
public class GuestService {

    @Inject
    private UserManager userManager;

    @Path("/")
    @GET
    public List<Guest> getAllGuest() {
        return userManager.filter(x -> x instanceof Guest).stream().map(x -> (Guest) x).collect(Collectors.toList());
    }

    @Path("/{uuid}")
    @GET
    public Guest getGuest(@PathParam("uuid") String id) {
        try {
            return (Guest) userManager.get(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            return (Guest) userManager.get(id);
        }
    }

    @Path("/")
    @POST
    public Guest addGuest(@Valid Guest guest) {
        userManager.add(guest);
        return (Guest) userManager.get(guest.getLogin());
    }

    @Path("/")
    @PUT
    public Guest updateGuest() {
        return null;
    }

}
