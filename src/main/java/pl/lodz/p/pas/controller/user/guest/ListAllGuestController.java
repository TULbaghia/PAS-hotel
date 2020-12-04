package pl.lodz.p.pas.controller.user.guest;

import lombok.Getter;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.repository.UserRepository;

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
    private UserRepository userRepository;

    @Getter
    private Map<UUID, Boolean> currentUserStatuses = new HashMap<>();

    public List<Guest> getAllGuest() {
        return userRepository.getAll().stream()
                .filter(x -> x instanceof Guest)
                .map(x -> (Guest) x)
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void initCurrentStatuses() {
        getAllGuest().forEach(x -> currentUserStatuses.put(x.getId(), x.isActive()));
    }

}
