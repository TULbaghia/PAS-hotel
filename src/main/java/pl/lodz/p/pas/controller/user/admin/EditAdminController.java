package pl.lodz.p.pas.controller.user.admin;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.pas.model.user.Admin;
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
public class EditAdminController implements Serializable {

    @Inject
    private UserRepository userRepository;

    @Inject
    private Conversation conversation;

    @Getter
    @Setter
    private Admin admin = new Admin();

    public String processToEditAdmin(Admin admin) throws InvocationTargetException, IllegalAccessException {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        BeanUtils.copyProperties(this.admin, admin);
        return "EditAdmin";
    }

    public String editAdmin() {
        try {
            userRepository.update(admin);
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage("adminForm", new FacesMessage(e.getMessage()));
            return null;
        }
        conversation.end();
        return "ListAllAdmin";
    }

}
