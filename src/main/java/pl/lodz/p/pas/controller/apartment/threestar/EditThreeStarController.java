package pl.lodz.p.pas.controller.apartment.threestar;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.model.resource.ThreeStarApartment;
import pl.lodz.p.pas.repository.exception.RepositoryException;

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
public class EditThreeStarController implements Serializable {
    @Inject
    private ApartmentManager apartmentManager;

    @Inject
    private Conversation conversation;

    @Getter
    @Setter
    private ThreeStarApartment threeStarApartment = new ThreeStarApartment();

    public String processToEditThreeStar(ThreeStarApartment threeStarApartment) throws InvocationTargetException, IllegalAccessException {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        BeanUtils.copyProperties(this.threeStarApartment, threeStarApartment);
        return "EditThreeStar";
    }

    public String editThreeStar() {
        try {
            apartmentManager.update(threeStarApartment);
        } catch (RepositoryException e) {
            FacesContext.getCurrentInstance().addMessage("threeStarForm", new FacesMessage(e.getMessage()));
            return null;
        }
        conversation.end();
        return "ListAllThreeStar";
    }
}
