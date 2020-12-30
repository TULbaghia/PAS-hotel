package pl.lodz.p.pas.controller.reservation;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.ReservationManager;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.resource.Reservation;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Getter
    @Setter
    private int currentPage = 1;

    @Getter
    @Setter
    private int itemsPerPage = 5;

    @Getter
    @Setter
    private List<Integer> availablePages;


    @PostConstruct
    public void initCurrentReservations() {
        if (request.isUserInRole("Guest")) {
            availablePages = IntStream.range(1, 1 + (int) Math.ceil((double) reservationManager.filterByResources(userManager.getCurrentUser(), searchDataGuest, searchDataApartment, searchDataAll).size() / itemsPerPage))
                    .boxed().collect(Collectors.toList());
            currentPage = Math.min(currentPage, availablePages.get(availablePages.size() - 1));
            currentReservation = reservationManager.paginate(itemsPerPage, currentPage, x -> x.getGuest().getId().equals(userManager.getCurrentUser().getId())
                    && x.getGuest().toString().contains(searchDataGuest)
                    && (x.getApartment() == null || x.getApartment().toString().contains(searchDataApartment))
                    && x.toString().contains(searchDataAll));


        } else {
            availablePages = IntStream.range(1, 1 + (int) Math.ceil((double) reservationManager.filterByResources(searchDataGuest, searchDataApartment, searchDataAll).size() / itemsPerPage))
                    .boxed().collect(Collectors.toList());
            currentPage = Math.min(currentPage, availablePages.get(availablePages.size() - 1));
            currentReservation = reservationManager.paginate(itemsPerPage, currentPage, x -> x.getGuest().toString().contains(searchDataGuest)
                    && (x.getApartment() == null || x.getApartment().toString().contains(searchDataApartment))
                    && x.toString().contains(searchDataAll));

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
