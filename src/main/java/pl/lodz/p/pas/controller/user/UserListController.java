package pl.lodz.p.pas.controller.user;

import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.user.UserRepository;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named
public class UserListController implements Serializable {

    @Inject
    private UserRepository userRepository;

    private List<User> currentUsers = new ArrayList<>();

    public List<User> getAllUsers() {
        return currentUsers;
    }

    public String deleteUser(User user) {
        userRepository.delete(user.getId());
        return "AllUsers";
    }

    @PostConstruct
    public void initCurrentPersons() {
        currentUsers = userRepository.getAll();
    }

}
