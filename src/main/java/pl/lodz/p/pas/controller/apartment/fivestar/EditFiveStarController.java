package pl.lodz.p.pas.controller.apartment.fivestar;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.pas.controller.functional.ResourceBundleService;
import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.model.resource.FiveStarApartment;
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
public class EditFiveStarController implements Serializable {
    @Inject
    private ApartmentManager apartmentManager;

    @Inject
    private Conversation conversation;

    @Getter
    @Setter
    private FiveStarApartment fiveStarApartment = new FiveStarApartment();

    @SneakyThrows
    public String processToEditFiveStar(FiveStarApartment fiveStarApartment) {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        if (apartmentManager.get(fiveStarApartment.getId()) == null) {
            FacesContext.getCurrentInstance().addMessage("apartmentForm", new FacesMessage(
                    ResourceBundleService.getBundle().getString("EditApartmentController.apartmentDoesNotExist")));
            return null;
        }
        BeanUtils.copyProperties(this.fiveStarApartment, fiveStarApartment);
        return "EditFiveStar";
    }

    public String editFiveStar() {
        try {
            apartmentManager.update(fiveStarApartment);
        } catch (RepositoryException e) {
            FacesContext.getCurrentInstance().addMessage("fiveStarForm", new FacesMessage(e.getMessage()));
            return null;
        }
        conversation.end();
        return "ListAllFiveStar";
    }
}
