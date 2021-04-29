package pl.lodz.p.pas.controller.user.manager;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.manager.exception.ManagerException;
import pl.lodz.p.pas.model.user.Manager;
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
public class EditManagerController implements Serializable {

    @Inject
    private UserManager userManager;

    @Inject
    private Conversation conversation;

    @Getter
    @Setter
    private Manager manager = new Manager();

    @SneakyThrows
    public String processToEditManager(Manager manager) {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        BeanUtils.copyProperties(this.manager, manager);
        return "EditManager";
    }

    public String editManager() {
        try {
            userManager.update(manager);
        } catch (RepositoryException | ManagerException e) {
            FacesContext.getCurrentInstance().addMessage("managerForm", new FacesMessage(e.getMessage()));
            return null;
        }
        conversation.end();
        return "ListAllManager";
    }

}
