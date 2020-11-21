package pl.lodz.p.pas.controller.apartment;

import pl.lodz.p.pas.model.apartment.Apartment;
import pl.lodz.p.pas.model.apartment.FiveStarApartment;
import pl.lodz.p.pas.model.apartment.ThreeStarApartment;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.apartment.ApartmentRepository;
import pl.lodz.p.pas.repository.user.UserRepository;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@ConversationScoped
@Named
public class ApartmentAdditionController implements Serializable {

    @Inject
    private ApartmentRepository apartmentRepository;

    @Inject
    private Conversation conversation;

    private Apartment newApartment = new ThreeStarApartment();

    public Apartment getNewApartment() {
        return newApartment;
    }

    public String processNewApartment() {
        newApartment.setApartmentId(UUID.randomUUID().toString());
        conversation.begin();
        return "ApartmentAdded";
    }

    public String confirmNewApartment() {
        if (newApartment.getApartmentId() == null) throw new IllegalArgumentException("Try to create apartment without personal data");
        apartmentRepository.add(newApartment);
        newApartment = new FiveStarApartment();
        conversation.end();
        return "Index";
    }

}
