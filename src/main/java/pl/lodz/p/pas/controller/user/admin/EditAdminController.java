package pl.lodz.p.pas.controller.user.admin;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.user.Admin;
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
public class EditAdminController implements Serializable {

    @Inject
    private UserManager userManager;

    @Inject
    private Conversation conversation;

    @Getter
    @Setter
    private Admin admin = new Admin();

    @SneakyThrows
    public String processToEditAdmin(Admin admin) {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        BeanUtils.copyProperties(this.admin, admin);
        return "EditAdmin";
    }

    public String editAdmin() {
        try {
            userManager.update(admin);
        } catch (RepositoryException e) {
            FacesContext.getCurrentInstance().addMessage("adminForm", new FacesMessage(e.getMessage()));
            return null;
        }
        conversation.end();
        return "ListAllAdmin";
    }

}
