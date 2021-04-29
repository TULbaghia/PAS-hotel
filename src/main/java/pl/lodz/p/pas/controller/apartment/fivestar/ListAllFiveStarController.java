package pl.lodz.p.pas.controller.apartment.fivestar;

import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.model.resource.FiveStarApartment;

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
public class ListAllFiveStarController implements Serializable {

    @Inject
    private ApartmentManager apartmentManager;

    private List<FiveStarApartment> currentApartments = new ArrayList<>();

    public List<FiveStarApartment> getAllApartment() {
        return currentApartments;
    }

    @PostConstruct
    public void initCurrentApartments() {
        currentApartments = apartmentManager.filter(x -> x instanceof FiveStarApartment).stream().map(x -> (FiveStarApartment) x).collect(Collectors.toList());
    }
}
