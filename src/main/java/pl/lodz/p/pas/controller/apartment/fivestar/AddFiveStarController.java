package pl.lodz.p.pas.controller.apartment.fivestar;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.model.resource.FiveStarApartment;
import pl.lodz.p.pas.repository.exception.RepositoryException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class AddFiveStarController implements Serializable {
    @Inject
    private ApartmentManager apartmentManager;

    @Getter
    @Setter
    private FiveStarApartment fiveStarApartment = new FiveStarApartment();

    public String createFiveStar() {
        try {
            apartmentManager.add(fiveStarApartment);
        } catch (RepositoryException e) {
            FacesContext.getCurrentInstance().addMessage("fiveStarForm", new FacesMessage(e.getMessage()));
            return null;
        }
        return "ListAllApartment";
    }
}
