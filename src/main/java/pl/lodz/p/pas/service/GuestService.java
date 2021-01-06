package pl.lodz.p.pas.service;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.json.JsonObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.json.JSONObject;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.service.dto.GuestDTO;
import pl.lodz.p.pas.service.views.Views;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    @JsonView
    public String getGuest(@PathParam("uuid") String id) throws JsonProcessingException {
        GuestDTO guestDTO = new GuestDTO();
        try {
            return guestDTO.writeAsString(Views.Public.class, (Guest) userManager.get(UUID.fromString(id)));
        } catch (IllegalArgumentException e) {
            return guestDTO.writeAsString(Views.Public.class, (Guest) userManager.get(id));
        }
    }

    @Path("/")
    @POST
    public String addGuest(@Valid Guest guest) throws JsonProcessingException {
        GuestDTO guestDTO = new GuestDTO();
        userManager.add(guest);
        return guestDTO.writeAsString(Views.Public.class, guest);
    }

    @Path("/{uuid}")
    @PUT
    public String updateGuest(@PathParam("uuid") String id, @Valid Guest guest) throws JsonProcessingException {
        GuestDTO guestDTO = new GuestDTO();
        Guest editingGuest = (Guest) userManager.get(UUID.fromString(id));
        BeanUtils.copyProperties();
        userManager.update(guest);
        return guestDTO.writeAsString(Views.Public.class, (Guest) userManager.get(UUID.fromString(id)));
    }

    @Path("/{uuid}")
    @PATCH
    public String activateGuest(@PathParam("uuid") String id, Guest guest) throws JsonProcessingException {
        GuestDTO guestDTO = new GuestDTO();
        Guest editingGuest = (Guest) userManager.get(UUID.fromString(id));
        editingGuest.setActive(guest.isActive());
        userManager.update(editingGuest);
        return guestDTO.writeAsString(Views.Public.class, (Guest) userManager.get(editingGuest.getLogin()));
    }

}
