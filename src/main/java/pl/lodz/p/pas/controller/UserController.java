package pl.lodz.p.pas.controller;

import javax.enterprise.context.RequestScoped;  
import pl.lodz.p.pas.model.user.User;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value="userController")
@RequestScoped
public class UserController {
    
    @Inject
    private User user;

    public UserController() {
    }

    public UserController(User user) {
        this.user = user;
    }
    
    public String addUser(){
        return "UserAdded";
    }
    
}
