package pl.lodz.p.pas.controller.reservation;

import pl.lodz.p.pas.model.apartment.Apartment;
import pl.lodz.p.pas.model.guest.Guest;
import pl.lodz.p.pas.model.reservation.Reservation;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.reservation.ReservationRepository;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@SessionScoped
@Named
public class ReservationAdditionController implements Serializable {

    @Inject
    private ReservationRepository reservationRepository;

    @Inject
    private Conversation conversation;

    private Reservation newReservation = new Reservation();

    public Reservation getNewReservation() {
        return newReservation;
    }

    public String processNewReservation(User u) {
        if (u == null) {
            throw new RuntimeException("TEST");
        }
        newReservation.setRentId(UUID.randomUUID().toString());
        if (u instanceof Guest) {
            newReservation.setGuest((Guest) u);
        } else {
            throw new IllegalArgumentException("Only guest can be attached to reservation");
        }
        return "SelectApartment";
    }

    public String connectApartment(Apartment a) {
        if (a == null) {
            throw new IllegalArgumentException("Invalid apartment");
        } else newReservation.setApartment(a);
        return "ReservationAdded";
    }

    public String confirmNewReservation() {
        if (newReservation.getApartment() == null)
            throw new IllegalArgumentException("Try to create reservation without personal data");
        newReservation.setReservationStartDate(LocalDateTime.now());
        reservationRepository.add(newReservation);
        newReservation = new Reservation();
//        conversation.end();
        return "Index";
    }

}
