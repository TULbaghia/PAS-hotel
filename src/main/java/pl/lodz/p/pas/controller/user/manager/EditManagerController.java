package pl.lodz.p.pas.controller.user.manager;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.pas.model.user.Manager;
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
public class EditManagerController implements Serializable {

    @Inject
    private UserRepository userRepository;

    @Inject
    private Conversation conversation;

    @Getter
    @Setter
    private Manager manager = new Manager();

    public String processToEditManager(Manager manager) throws InvocationTargetException, IllegalAccessException {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        BeanUtils.copyProperties(this.manager, manager);
        return "EditManager";
    }

    public String editManager() {
        try {
            userRepository.update(manager.getId(), manager);
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage("managerForm", new FacesMessage(e.getMessage()));
            return null;
        }
        conversation.end();
        return "ListAllManager";
    }

}
