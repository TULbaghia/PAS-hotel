package pl.lodz.p.pas.controller.apartment;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.controller.functional.ResourceBundleService;
import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.manager.ReservationManager;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.model.resource.FiveStarApartment;
import pl.lodz.p.pas.model.resource.Reservation;
import pl.lodz.p.pas.model.resource.ThreeStarApartment;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ConversationScoped
@Named
public class DetailApartmentController implements Serializable {

    @Inject
    private ApartmentManager apartmentManager;

    @Inject
    private ReservationManager reservationManager;

    @Inject
    private Conversation conversation;

    @Getter
    @Setter
    private Apartment apartment;

    public String processToShowDetails(Apartment a) {
        if(conversation.isTransient())
            conversation.begin();
        if (apartmentManager.get(a.getId()) == null) {
            FacesContext.getCurrentInstance().addMessage("apartmentForm", new FacesMessage(
                    ResourceBundleService.getBundle().getString("DetailApartmentController.apartmentDoesNotExist")));
            return null;
        }
        apartment = a;
        return "DetailApartment";
    }

    public void endTransact() {
        conversation.end();
    }

    public List<Reservation> getApartmentReservations() {
        return reservationManager.getApartmentReservations(apartment);
    }

    public String getInstanceName() {
        return apartment instanceof FiveStarApartment ? "FiveStarApartment" : "ThreeStarApartment";
    }

    public ThreeStarApartment getAsThreeStar() {
        return (ThreeStarApartment) apartment;
    }

    public FiveStarApartment getAsFiveStar() {
        return (FiveStarApartment) apartment;
    }
}
