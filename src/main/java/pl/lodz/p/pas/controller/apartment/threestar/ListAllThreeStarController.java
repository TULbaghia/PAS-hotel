package pl.lodz.p.pas.controller.apartment.threestar;

import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.model.resource.ThreeStarApartment;

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
public class ListAllThreeStarController implements Serializable {
    @Inject
    private ApartmentManager apartmentManager;

    private List<ThreeStarApartment> currentApartments = new ArrayList<>();

    public List<ThreeStarApartment> getAllApartment() {
        return currentApartments;
    }

    @PostConstruct
    public void initCurrentApartments() {
        currentApartments = apartmentManager.filter(x -> x instanceof ThreeStarApartment).stream().map(x -> (ThreeStarApartment) x).collect(Collectors.toList());
    }
}
