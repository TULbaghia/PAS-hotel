package pl.lodz.p.pas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.model.user.Manager;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.service.dto.GuestDTO;
import pl.lodz.p.pas.service.views.Views;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

public class UserAbstractService <T extends User> {
    @Inject @Getter
    private UserManager userManager;

    private final T helpVariable;

    public UserAbstractService(T user) {
        helpVariable = user;
    }

    public String getAllUsers() {
        return new GuestDTO().writeAsString(Views.Public.class, new ArrayList<>(userManager.filter(x -> x.getClass().isInstance(helpVariable))));
    }

    public String getUser(String id, SecurityContext securityContext) {
        String currentUser = securityContext.getUserPrincipal().getName();
        User user;
        try {
            user = userManager.get(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            user = userManager.get(id);
        }
        if ((userManager.get(currentUser) instanceof Manager || currentUser.equals(user.getLogin())) && helpVariable.getClass().isInstance(user)) {
            return new GuestDTO().writeAsString(Views.Public.class, user);
        }
        return null;
    }

    public String addUserA(User user) {
        userManager.add(user);
        return new GuestDTO().writeAsString(Views.Public.class, user);
    }

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

    public String activateGuest(Guest guest) throws JsonProcessingException {
        GuestDTO guestDTO = new GuestDTO();
        Guest editingGuest = (Guest) userManager.get(guest.getId());
        editingGuest.setActive(guest.isActive());
        userManager.update(editingGuest);
        return guestDTO.writeAsString(Views.Public.class, (Guest) userManager.get(editingGuest.getLogin()));
    }
}
