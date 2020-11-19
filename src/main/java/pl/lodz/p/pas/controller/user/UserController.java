package pl.lodz.p.pas.controller.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import pl.lodz.p.pas.model.user.User;
import javax.inject.Named;
import pl.lodz.p.pas.repository.user.UserRepository;

@SessionScoped
@Named
public class UserController implements Serializable {
    
    @Inject
    private UserRepository userRepository;
    
    private List<User> currentUsers = new ArrayList<>();
    
    private User newUser = new User();
    
    public List<User> getAllUsers(){
        return currentUsers;
    }

    public User getNewUser() {
        return newUser;
    }

    public String processNewPerson(){
        newUser.setId(UUID.randomUUID().toString());
        return "UserAdded";
    }

    public String confirmNewUser() {
        if (newUser.getLogin() == null) throw new IllegalArgumentException("Try to create user without personal data");
        userRepository.add(newUser);
        newUser = new User();
        initCurrentPersons();
        return "Index";
    }

    public void deleteUser(User user) {
        userRepository.delete(user.getId());
        initCurrentPersons();
    }

    @PostConstruct
    public void initCurrentPersons() {
        currentUsers = userRepository.getAll();
    }
    
}
