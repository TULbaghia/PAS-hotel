package pl.lodz.p.pas.controller.apartment.threestar;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.model.resource.ThreeStarApartment;
import pl.lodz.p.pas.repository.exception.RepositoryException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class AddThreeStarController implements Serializable {
    @Inject
    private ApartmentManager apartmentManager;

    @Getter
    @Setter
    private ThreeStarApartment threeStarApartment = new ThreeStarApartment();

    public String createThreeStar() {
        try {
            apartmentManager.add(threeStarApartment);
        } catch (RepositoryException e) {
            FacesContext.getCurrentInstance().addMessage("threeStarForm", new FacesMessage(e.getMessage()));
            return null;
        }
        return "ListAllApartment";
    }
}
