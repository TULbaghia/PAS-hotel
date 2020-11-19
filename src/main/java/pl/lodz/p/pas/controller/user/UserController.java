package pl.lodz.p.pas.controller.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    
    public String processNewPerson(){
        return "UserAdded";
    }
    
    public String confirmNewUser() {
        userRepository.add(newUser);
        return "Index";
    }

    public User getNewUser() {
        return newUser;
    }
    
    @PostConstruct
    public void initCurrentPersons() {
        System.out.println(userRepository.getAll());
        currentUsers = userRepository.getAll();
    }
    
}
