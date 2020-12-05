package pl.lodz.p.pas.controller.user.guest;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.ReservationManager;
import pl.lodz.p.pas.model.resource.Reservation;
import pl.lodz.p.pas.model.user.Guest;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ConversationScoped
@Named
public class DetailGuestController implements Serializable {

    @Inject
    private ReservationManager reservationManager;

    @Inject
    private Conversation conversation;

    @Getter
    @Setter
    private Guest guest = new Guest();

    public String processToShowDetails(Guest g) {
        if(conversation.isTransient())
        conversation.begin();
        guest = g;
        return "DetailGuest";
    }

    public List<Reservation> getGuestsReservations() {
        if(!conversation.isTransient())
        conversation.end();
        return reservationManager.getGuestReservations(guest);
    }
}
