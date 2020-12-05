package pl.lodz.p.pas.controller.apartment;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.repository.ApartmentRepository;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named
public class ListAllApartmentController implements Serializable {

    @Inject
    private ApartmentManager apartmentManager;

    private List<Apartment> currentApartments = new ArrayList<>();

    @Getter @Setter
    private String searchData = "";

    public List<Apartment> getAllApartments() {
        return currentApartments;
    }

    @PostConstruct
    public void initCurrentApartments() {
        currentApartments = apartmentManager.filter( x -> x.toString().contains(searchData));
    }

}
