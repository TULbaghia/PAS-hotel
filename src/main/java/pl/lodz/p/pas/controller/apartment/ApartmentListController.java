package pl.lodz.p.pas.controller.apartment;

import pl.lodz.p.pas.model.apartment.Apartment;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.apartment.ApartmentRepository;
import pl.lodz.p.pas.repository.user.UserRepository;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named
public class ApartmentListController implements Serializable {

    @Inject
    private ApartmentRepository apartmentRepository;

    private List<Apartment> currentApartments = new ArrayList<>();

    public List<Apartment> getAllApartments() {
        return currentApartments;
    }

    public String deleteApartment(Apartment apartment) {
        apartmentRepository.delete(apartment.getApartmentId());
        return "AllApartments";
    }

    @PostConstruct
    public void initCurrentApartments() {
        currentApartments = apartmentRepository.getAll();
    }

}
