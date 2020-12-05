package pl.lodz.p.pas.controller.user.guest;

import lombok.Getter;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.user.Guest;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class ListAllGuestController implements Serializable {

    @Inject
    private UserManager userManager;

    @Getter
    private Map<UUID, Boolean> currentUserStatuses = new HashMap<>();

    public List<Guest> getAllGuest() {
        return userManager.filter(x -> x instanceof Guest).stream().map(x -> (Guest) x).collect(Collectors.toList());
    }

    @PostConstruct
    public void initCurrentStatuses() {
        userManager.filter(x -> x instanceof Guest).forEach(x -> currentUserStatuses.put(x.getId(), x.isActive()));
    }

}
