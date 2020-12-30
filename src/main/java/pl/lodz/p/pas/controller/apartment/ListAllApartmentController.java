package pl.lodz.p.pas.controller.apartment;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.controller.functional.PaginationController;
import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.model.resource.Apartment;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ViewScoped
@Named
public class ListAllApartmentController implements Serializable {

    @Inject
    private ApartmentManager apartmentManager;

    @Inject
    private PaginationController pController;

    private List<Apartment> currentApartments = new ArrayList<>();

    @Getter
    @Setter
    private List<Integer> availablePages;

    @Getter @Setter
    private String searchData = "";

    public List<Apartment> getAllApartments() {
        return currentApartments;
    }

    @PostConstruct
    public void initCurrentApartments() {
        availablePages = IntStream.range(1, 1 + (int) Math.ceil((double) apartmentManager.filter( x -> x.toString().contains(searchData)).size() / pController.getApartmentItemsPerPage()))
                .boxed().collect(Collectors.toList());
        pController.setApartmentCurrentPage(Math.min(pController.getApartmentCurrentPage(), availablePages.get(availablePages.size() - 1)));
        currentApartments = apartmentManager.paginate(pController.getApartmentItemsPerPage(), pController.getApartmentCurrentPage(), x -> x.toString().contains(searchData));
    }

}
