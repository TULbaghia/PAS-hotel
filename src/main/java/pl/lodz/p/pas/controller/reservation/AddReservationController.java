package pl.lodz.p.pas.controller.reservation;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.manager.ReservationManager;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.model.resource.Reservation;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.repository.exception.RepositoryException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class AddReservationController implements Serializable {

    @Inject
    private HttpServletRequest request;

    @Inject
    private ReservationManager reservationManager;

    @Inject
    private ApartmentManager apartmentManager;

    @Inject
    private UserManager userManager;

    @Getter @Setter
    private Reservation reservation = new Reservation();

    public String createReservation() {
        if(request.isUserInRole("Guest")) {
            reservation.setGuest((Guest) userManager.getCurrentUser());
        }
        try {
            reservationManager.add(reservation);
        } catch (RepositoryException e) {
            FacesContext.getCurrentInstance().addMessage("reservationForm", new FacesMessage(e.getMessage()));
            return null;
        }
        return "ListAllReservation";
    }

    public List<Apartment> getAvaliableApartment() {
        List<Apartment> usedApartments = reservationManager.getAll(true).stream().map(Reservation::getApartment).collect(Collectors.toList());
        return apartmentManager.filter(x -> !usedApartments.contains(x));
    }

}
