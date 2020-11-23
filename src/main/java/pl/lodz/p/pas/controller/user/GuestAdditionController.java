package pl.lodz.p.pas.controller.user;

import pl.lodz.p.pas.model.guest.Guest;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.user.UserRepository;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@ConversationScoped
@Named
public class GuestAdditionController implements Serializable {

    @Inject
    private UserRepository userRepository;

    @Inject
    private Conversation conversation;

    private User newUser = new Guest();

    public User getNewUser() {
        return newUser;
    }

    public String processNewPerson() {
        conversation.begin();
        return "GuestAdded";
    }

    public String confirmNewUser() {
        if (newUser.getLogin() == null) throw new IllegalArgumentException("Try to create user without personal data");
        userRepository.add(newUser);
        newUser = new Guest();
        conversation.end();
        return "Index";
    }

}
