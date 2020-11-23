package pl.lodz.p.pas.model.guest;

import lombok.*;
import pl.lodz.p.pas.model.guest.exception.GuestException;
import pl.lodz.p.pas.model.guest.guesttype.BasicGuestType;
import pl.lodz.p.pas.model.guest.guesttype.GuestType;
import pl.lodz.p.pas.model.user.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Guest extends User implements Serializable {
    @Setter private int numberOfStays;
    private GuestType guestType;

    public Guest(String login, String lastname, String password) {
        super(login, lastname, password);
        this.guestType = new BasicGuestType();
        this.numberOfStays = 0;
    }

    public Guest() {
        this("", "", "");
    }

    public double getDiscount(double price) throws GuestException {
        return guestType.calculateDiscount(price);
    }

    public void changeGuestType(GuestType guestType) throws GuestException {
        if (guestType == null) {
            throw new GuestException("New Guest type is null.");
        } else {
            this.guestType = guestType;
        }
    }

    public int getMaxApartmentsNumber() {
        return this.guestType.getMaxApartmentsNumber();
    }
}
