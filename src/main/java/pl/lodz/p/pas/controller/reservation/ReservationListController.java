package pl.lodz.p.pas.controller.reservation;

import pl.lodz.p.pas.model.reservation.Reservation;
import pl.lodz.p.pas.repository.reservation.ReservationRepository;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named
public class ReservationListController implements Serializable {

    @Inject
    private ReservationRepository reservationRepository;

    private List<Reservation> currentReservations = new ArrayList<>();

    public List<Reservation> getAllReservations() {
        return currentReservations;
    }

    public String deleteReservation(Reservation reservation) {
        reservationRepository.delete(reservation.getRentId());
        return "AllReservations";
    }

    @PostConstruct
    public void initCurrentReservations() {
        currentReservations = reservationRepository.getAll();
    }

}
