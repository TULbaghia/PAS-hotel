package pl.lodz.p.pas.controller.reservation;

import pl.lodz.p.pas.manager.ReservationManager;
import pl.lodz.p.pas.model.exception.GuestException;
import pl.lodz.p.pas.model.exception.ReservationException;
import pl.lodz.p.pas.model.resource.Reservation;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class EndReservationController implements Serializable {

    @Inject
    private ReservationManager reservationManager;

    public String endReservation(Reservation reservation) {
        try {
            reservationManager.endReservation(reservation.getId());
        } catch (ReservationException | GuestException e) {
            FacesContext.getCurrentInstance().addMessage("reservationForm", new FacesMessage(e.getMessage()));
            return null;
        }
        return "ListAllReservation";
    }

}
