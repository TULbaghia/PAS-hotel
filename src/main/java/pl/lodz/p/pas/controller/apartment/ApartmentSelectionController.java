package pl.lodz.p.pas.controller.apartment;

import pl.lodz.p.pas.model.apartment.Apartment;
import pl.lodz.p.pas.repository.apartment.ApartmentRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Named
public class ApartmentSelectionController implements Serializable {

    @Inject
    private ApartmentRepository apartmentRepository;

    private List<Apartment> currentApartments = new ArrayList<>();

    public List<Apartment> getAllApartments() {
        return currentApartments;
    }

    @PostConstruct
    public void initCurrentPersons() {
        currentApartments = apartmentRepository.getAll();
    }

    public String connectApartment(Apartment a) {
        if (a == null) {
            throw new IllegalArgumentException("Invalid apartment");
        } else {
//            this. = a;
        }
        return "ReservationAdded";
    }

}
