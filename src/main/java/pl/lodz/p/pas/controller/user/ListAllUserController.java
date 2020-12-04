package pl.lodz.p.pas.controller.user;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class ListAllUserController implements Serializable {

    @Inject
    private UserRepository userRepository;

    @Getter
    private Map<UUID, Boolean> currentUserStatuses = new HashMap<>();

    @Getter
    private List<User> currentUsers = new ArrayList<>();

    @Getter @Setter
    private String searchData = "";

    public List<User> getAllUsers() {
        return currentUsers;
    }

    public List<User> getUser() {
        return userRepository.getAll().stream().filter(x -> x.toString().contains(searchData)).collect(Collectors.toList());
    }

    @PostConstruct
    public void initCurrentStatuses() {
        currentUserStatuses = userRepository.getAllActiveUsers();
        currentUsers = userRepository.filterBy(searchData);
    }

}
