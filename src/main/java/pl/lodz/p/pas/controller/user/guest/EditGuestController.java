package pl.lodz.p.pas.controller.user.guest;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.manager.exception.ManagerException;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.repository.exception.RepositoryException;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ConversationScoped
@Named
public class EditGuestController implements Serializable {

    @Inject
    private UserManager userManager;

    @Inject
    private Conversation conversation;

    @Getter
    @Setter
    private Guest guest = new Guest();

    @SneakyThrows
    public String processToEditGuest(Guest guest) {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        BeanUtils.copyProperties(this.guest, guest);
        return "EditGuest";
    }

    public String editGuest() {
        try {
            userManager.update(guest);
        } catch (RepositoryException | ManagerException e) {
            FacesContext.getCurrentInstance().addMessage("guestForm", new FacesMessage(e.getMessage()));
            return null;
        }
        conversation.end();
        return "ListAllGuest";
    }

}
