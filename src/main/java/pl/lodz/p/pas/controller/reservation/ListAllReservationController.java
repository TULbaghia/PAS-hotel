package pl.lodz.p.pas.controller.reservation;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.controller.functional.PaginationController;
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
import java.util.function.Predicate;
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

    @Inject
    private PaginationController pController;

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
    private List<Integer> availablePages;


    @PostConstruct
    public void initCurrentReservations() {
        Predicate<Reservation> predicate = x -> x.getGuest().toString().contains(searchDataGuest)
                && (x.getApartment() == null || x.getApartment().toString().contains(searchDataApartment))
                && x.toString().contains(searchDataAll);

        if (request.isUserInRole("Guest")) {
            predicate = predicate.and(x -> x.getGuest().getId().equals(userManager.getCurrentUser().getId()));
        }

        int sizeActive = reservationManager.paginate(Integer.MAX_VALUE, 1, predicate.and(x -> x.getReservationEndDate() == null)).size();
        int sizeEnded = reservationManager.paginate(Integer.MAX_VALUE, 1, predicate.and(x -> x.getReservationEndDate() != null)).size();

        availablePages = IntStream.range(1, 1 + (int) Math.ceil((double) Math.max(sizeActive, sizeEnded) / pController.getReservationItemsPerPage())).boxed().collect(Collectors.toList());

        if(availablePages.size() > 0) {
            pController.setReservationCurrentPage(Math.min(pController.getReservationCurrentPage(), availablePages.get(availablePages.size() - 1)));
        }
        currentReservation = reservationManager.paginate(pController.getReservationItemsPerPage(), pController.getReservationCurrentPage(), predicate);

        allReservationsActive = reservationManager.paginate(pController.getReservationItemsPerPage(), pController.getReservationCurrentPage(), predicate.and(x -> x.getReservationEndDate() == null));
        allReservationsEnded = reservationManager.paginate(pController.getReservationItemsPerPage(), pController.getReservationCurrentPage(), predicate.and(x -> x.getReservationEndDate() != null));
    }

}
