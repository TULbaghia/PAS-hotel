package pl.lodz.p.pas.model.user;

import javax.inject.Named;  
import javax.enterprise.context.RequestScoped;  

@RequestScoped  
@Named
public class User {

    public User() {}

    private Integer Id;
    private String login = "Testowy test";

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
//        System.out.println("Ustawiam id" + this);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
//        System.out.println("Ustawiam login" + this);
    }    
}
