package pl.lodz.p.pas.controller.user;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.user.User;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@ViewScoped
@Named
public class ListAllUserController implements Serializable {

    @Inject
    private UserManager userManager;

    @Getter
    private Map<UUID, Boolean> currentUserStatuses = new HashMap<>();

    @Getter
    private List<User> currentUsers = new ArrayList<>();

    @Getter @Setter
    private String searchData = "";

    public List<User> getAllUsers() {
        return currentUsers;
    }

    @PostConstruct
    public void initCurrentStatuses() {
        userManager.getAll().forEach(x -> currentUserStatuses.put(x.getId(), x.isActive()));
        currentUsers = userManager.filter(x -> x.toString().contains(searchData));
    }

}
