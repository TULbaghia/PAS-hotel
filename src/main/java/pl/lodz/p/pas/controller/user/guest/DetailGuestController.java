package pl.lodz.p.pas.controller.user.guest;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.repository.ReservationRepository;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ConversationScoped
@Named
public class DetailGuestController implements Serializable {

    @Inject
    private ReservationRepository reservationRepository;

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

    public void endTransact() {
        conversation.end();
    }

    public List<Reservation> getGuestsReservations() {
//        conversation.end();
        return reservationRepository.getGuestsReservations(guest);
    }
}
