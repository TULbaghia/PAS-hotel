package pl.lodz.p.pas.controller.reservation;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.ReservationManager;
import pl.lodz.p.pas.model.resource.Reservation;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class ListAllReservationController implements Serializable {

    @Inject
    private ReservationManager reservationManager;

    @Getter
    private List<Reservation> currentReservation = new ArrayList<>();

    @Getter
    private List<Reservation> allReservationsActive = new ArrayList<>();

    @Getter
    private List<Reservation> allReservationsEnded = new ArrayList<>();

    @Getter @Setter
    private String searchDataGuest = "";

    @Getter @Setter
    private String searchDataApartment = "";

    @Getter @Setter
    private String searchDataAll = "";

    @PostConstruct
    public void initCurrentReservations() {
        currentReservation = reservationManager.filterByResources(searchDataGuest, searchDataApartment, searchDataAll);
        allReservationsActive.clear();
        allReservationsEnded.clear();
        currentReservation.forEach(x -> {
            if (x.getReservationEndDate() == null) {
                allReservationsActive.add(x);
            } else {
                allReservationsEnded.add(x);
            }
        });
    }
}
