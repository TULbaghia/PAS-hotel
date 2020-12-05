package pl.lodz.p.pas.controller.reservation;

import pl.lodz.p.pas.manager.ReservationManager;
import pl.lodz.p.pas.model.resource.Reservation;
import pl.lodz.p.pas.repository.exception.RepositoryException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class DeleteReservationController implements Serializable {

    @Inject
    private ReservationManager reservationManager;

    @Inject ListAllReservationController listAllReservationController;

    public String deleteReservation(Reservation reservation) {
        try {
            reservationManager.delete(reservation.getId());
        } catch (RepositoryException e) {
            FacesContext.getCurrentInstance().addMessage("reservationForm", new FacesMessage(e.getMessage()));
            return null;
        }
        listAllReservationController.initCurrentReservations();
        return "ListAllReservation";
    }
}
