package pl.lodz.p.pas.controller.reservation;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.ReservationManager;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.resource.Reservation;
import pl.lodz.p.pas.model.user.Guest;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class ListAllReservationController implements Serializable {

    @Inject
    private HttpServletRequest request;

    @Inject
    private ReservationManager reservationManager;

    @Inject
    private UserManager userManager;

    @Getter
    private List<Reservation> currentReservation = new ArrayList<>();

    @Getter
    private List<Reservation> allReservationsActive = new ArrayList<>();

    @Getter
    private List<Reservation> allReservationsEnded = new ArrayList<>();

    @Getter
    @Setter
    private String searchDataGuest = "";

    @Getter
    @Setter
    private String searchDataApartment = "";

    @Getter
    @Setter
    private String searchDataAll = "";

    @PostConstruct
    public void initCurrentReservations() {
        if (request.isUserInRole("Guest")) {
            currentReservation = reservationManager.filterByResources(userManager.getCurrentUser(), searchDataGuest, searchDataApartment, searchDataAll);
        } else {
            currentReservation = reservationManager.filterByResources(searchDataGuest, searchDataApartment, searchDataAll);
        }
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
