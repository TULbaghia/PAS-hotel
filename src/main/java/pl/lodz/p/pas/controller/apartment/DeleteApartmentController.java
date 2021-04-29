package pl.lodz.p.pas.controller.apartment;

import pl.lodz.p.pas.controller.apartment.fivestar.ListAllFiveStarController;
import pl.lodz.p.pas.controller.apartment.threestar.ListAllThreeStarController;
import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.manager.exception.ManagerException;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.repository.exception.RepositoryException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class DeleteApartmentController implements Serializable {

    @Inject
    private ApartmentManager apartmentManager;

    @Inject
    ListAllApartmentController listAllApartmentController;

    @Inject
    ListAllThreeStarController listAllThreeStarController;

    @Inject
    ListAllFiveStarController listAllFiveStarController;

    public String deleteApartment(Apartment apartment, String view) {
        if (apartment != null) {
            try {
                apartmentManager.delete(apartment.getId());
            } catch (ManagerException | RepositoryException e) {
                FacesContext.getCurrentInstance().addMessage("apartmentForm", new FacesMessage(e.getMessage()));
                return null;
            }
        }
        listAllApartmentController.initCurrentApartments();
        listAllFiveStarController.initCurrentApartments();
        listAllThreeStarController.initCurrentApartments();

        return view;
    }

}
