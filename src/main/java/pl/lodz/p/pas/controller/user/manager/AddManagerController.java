package pl.lodz.p.pas.controller.user.manager;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.manager.exception.ManagerException;
import pl.lodz.p.pas.model.user.Manager;
import pl.lodz.p.pas.repository.exception.RepositoryException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class AddManagerController implements Serializable {
    @Inject
    private UserManager userManager;

    @Getter
    @Setter
    private Manager manager = new Manager();

    public String createManager() {
        try {
            userManager.add(manager);
        } catch (RepositoryException | ManagerException e) {
            FacesContext.getCurrentInstance().addMessage("managerForm", new FacesMessage(e.getMessage()));
            return null;
        }
        return "ListAllManager";
    }

}
