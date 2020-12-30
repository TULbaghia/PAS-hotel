package pl.lodz.p.pas.controller.user.guest;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.manager.exception.ManagerException;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.repository.exception.RepositoryException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class AddGuestController implements Serializable {
    @Inject
    private UserManager userManager;

    @Getter
    @Setter
    private Guest guest = new Guest();

    public String createGuest() {
        try {
            userManager.add(guest);
        } catch (RepositoryException | ManagerException e) {
            FacesContext.getCurrentInstance().addMessage("guestForm", new FacesMessage(e.getMessage()));
            return null;
        }
        return "ListAllGuest";
    }

}
