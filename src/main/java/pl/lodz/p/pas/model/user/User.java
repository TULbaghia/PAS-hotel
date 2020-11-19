package pl.lodz.p.pas.model.user;

import java.io.Serializable;
import java.util.UUID;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@SessionScoped
@Named
public class User implements Serializable {
    
    private String id;
    private String login;
    private String name;
    private String surname;
    
    public User(String id, String login, String name, String surname) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
    }
    
     public User(String login, String name, String surname) {
        this(UUID.randomUUID().toString(), login, name, surname);
    }
     
    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login=" + login + ", name=" + name + ", surname=" + surname + '}';
    }

}
