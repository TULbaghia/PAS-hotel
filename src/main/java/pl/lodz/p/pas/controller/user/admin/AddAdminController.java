package pl.lodz.p.pas.controller.user.admin;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.user.Admin;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class AddAdminController implements Serializable {
    @Inject
    private UserManager userManager;

    @Getter
    @Setter
    private Admin admin = new Admin();

    public String createAdmin() {
        try {
            userManager.add(admin);
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage("adminForm", new FacesMessage(e.getMessage()));
            return null;
        }
        return "ListAllAdmin";
    }

}
