package pl.lodz.p.pas.controller.user.guest;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.repository.UserRepository;

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
    private UserRepository userRepository;

    @Getter
    @Setter
    private Guest guest = new Guest();

    public String createGuest() {
        try {
            userRepository.add(guest);
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage("guestForm", new FacesMessage(e.getMessage()));
            return null;
        }
        return "ListAllGuest";
    }

}
