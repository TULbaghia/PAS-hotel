package pl.lodz.p.pas.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@ToString
@Getter
@Setter
public abstract class User implements Serializable {

    private String id;
    private String login;
    private String firstname;
    private String surname;

    public User(String id, String login, String firstname, String surname) {
        this.id = id;
        this.login = login;
        this.firstname = firstname;
        this.surname = surname;
    }

    public User(String login, String name, String surname) {
        this(UUID.randomUUID().toString(), login, name, surname);
    }
}
