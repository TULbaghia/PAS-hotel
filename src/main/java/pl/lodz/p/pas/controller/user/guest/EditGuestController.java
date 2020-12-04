package pl.lodz.p.pas.controller.user.guest;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.repository.UserRepository;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@ConversationScoped
@Named
public class EditGuestController implements Serializable {

    @Inject
    private UserRepository userRepository;

    @Inject
    private Conversation conversation;

    @Getter
    @Setter
    private Guest guest = new Guest();

    public String processToEditGuest(Guest guest) throws InvocationTargetException, IllegalAccessException {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        BeanUtils.copyProperties(this.guest, guest);
        return "EditGuest";
    }

    public String editGuest() {
        try {
            userRepository.update(guest.getId(), guest);
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage("guestForm", new FacesMessage(e.getMessage()));
            return null;
        }
        conversation.end();
        return "ListAllGuest";
    }

}
