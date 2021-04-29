package pl.lodz.p.pas.controller.apartment;

import pl.lodz.p.pas.controller.apartment.fivestar.EditFiveStarController;
import pl.lodz.p.pas.controller.apartment.threestar.EditThreeStarController;
import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.model.resource.FiveStarApartment;
import pl.lodz.p.pas.model.resource.ThreeStarApartment;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.InvocationTargetException;

@RequestScoped
@Named
public class EditApartmentController {

    @Inject
    private EditThreeStarController editThreeStarController;

    @Inject
    private EditFiveStarController editFiveStarController;

    public String processToEditApartment(Apartment apartment) throws InvocationTargetException, IllegalAccessException {
        if (apartment instanceof FiveStarApartment){
            return editFiveStarController.processToEditFiveStar((FiveStarApartment) apartment);
        } else {
            return editThreeStarController.processToEditThreeStar((ThreeStarApartment) apartment);
        }
    }
}
