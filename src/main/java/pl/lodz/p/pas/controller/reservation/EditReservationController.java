package pl.lodz.p.pas.controller.reservation;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.ReservationManager;
import pl.lodz.p.pas.model.resource.Reservation;
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
public class EditReservationController implements Serializable {
    @Inject
    private ReservationManager reservationManager;

    @Inject
    private Conversation conversation;

    @Getter
    @Setter
    private Reservation reservation = new Reservation();

    public String processToEditReservation(Reservation reservation) {
        if (conversation.isTransient()) {
            conversation.begin();
        }
        this.reservation = reservation;
        return "EditReservation";
    }

    public String editReservation() {
        try {
            reservationManager.update(reservation);
        } catch (RepositoryException e) {
            FacesContext.getCurrentInstance().addMessage("reservationForm", new FacesMessage(e.getMessage()));
            return null;
        }
        conversation.end();
        return "ListAllReservation";
    }
}
