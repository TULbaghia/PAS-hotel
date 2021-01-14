package pl.lodz.p.pas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.model.user.Manager;
import pl.lodz.p.pas.service.dto.GuestDTO;
import pl.lodz.p.pas.service.filters.userFilters.activateUserFilter.PatchActivateUserCheckBinding;
import pl.lodz.p.pas.service.filters.userFilters.addUserFilter.PostAddUserCheckBinding;
import pl.lodz.p.pas.service.filters.userFilters.updateUserFilter.PutUpdateUserCheckBinding;
import pl.lodz.p.pas.service.views.Views;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.lang.reflect.InvocationTargetException;
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
    public String getAllGuest() throws JsonProcessingException {
        GuestDTO guestDTO = new GuestDTO();
        return guestDTO.writeAsString(Views.Public.class,
                userManager.filter(x -> x instanceof Guest).stream().map(x -> (Guest) x).collect(Collectors.toList()));
    }

    @GET
    @Path("/{uuid}")
    public String getGuest(@PathParam("uuid") String id, @Context SecurityContext securityContext) throws JsonProcessingException {
        GuestDTO guestDTO = new GuestDTO();
        String currentUser = securityContext.getUserPrincipal().getName();
        Guest guest = (Guest) userManager.get(UUID.fromString(id));
        if (userManager.get(currentUser) instanceof Manager || currentUser.equals(guest.getLogin())) {
            try {
                return guestDTO.writeAsString(Views.Public.class, guest);
            } catch (IllegalArgumentException e) {
                return guestDTO.writeAsString(Views.Public.class, (Guest) userManager.get(id));
            }
        }
        return null;
    }

    @Path("/")
    @POST
    @PostAddUserCheckBinding
    public String addGuest(@Valid Guest guest) throws JsonProcessingException {
        userManager.add(guest);
        return new GuestDTO().writeAsString(Views.Public.class, guest);
    }

    @Path("/")
    @PUT
    @PutUpdateUserCheckBinding
    public String updateGuest(@Valid Guest guest) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        GuestDTO guestDTO = new GuestDTO();
        Guest editingGuest = new Guest();
        BeanUtils.copyProperties(editingGuest, userManager.get(guest.getId()));
        editingGuest.setLastname(guest.getLastname());
        editingGuest.setPassword(guest.getPassword());
        editingGuest.setFirstname(guest.getFirstname());
        userManager.update(editingGuest);
        return guestDTO.writeAsString(Views.Public.class, (Guest) userManager.get(guest.getId()));
    }

    @Path("/")
    @PATCH
    @PatchActivateUserCheckBinding
    public String activateGuest(Guest guest) throws JsonProcessingException {
        GuestDTO guestDTO = new GuestDTO();
        Guest editingGuest = (Guest) userManager.get(guest.getId());
        editingGuest.setActive(guest.isActive());
        userManager.update(editingGuest);
        return guestDTO.writeAsString(Views.Public.class, (Guest) userManager.get(editingGuest.getLogin()));
    }
}
