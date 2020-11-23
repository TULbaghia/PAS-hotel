package pl.lodz.p.pas.model.user;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class User implements Serializable {

    private String id;
    private String login;
    private String lastname;
    private String password;
    private boolean isActive;

    public User(String login, String lastname, String password) {
        this(UUID.randomUUID().toString(), login, lastname, password, true);
    }
}
