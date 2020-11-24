package pl.lodz.p.pas.controller.apartment;

import pl.lodz.p.pas.model.apartment.Apartment;
import pl.lodz.p.pas.model.reservation.Reservation;
import pl.lodz.p.pas.repository.apartment.ApartmentRepository;
import pl.lodz.p.pas.repository.reservation.ReservationRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class ApartmentSelectionController implements Serializable {

    @Inject
    private ApartmentRepository apartmentRepository;

    @Inject
    private ReservationRepository reservationRepository;

    private List<Apartment> currentRentedApartments = new ArrayList<>();

    public List<Apartment> getAllApartments() {
        return currentRentedApartments;
    }

    @PostConstruct
    public void getNotReservedApartments() {
        List<Apartment> rentApartments = reservationRepository.getAll().stream().map(Reservation::getApartment).collect(Collectors.toList());
        currentRentedApartments = apartmentRepository.getAll().stream().filter(x -> !rentApartments.contains(x)).collect(Collectors.toList());
    }
}
