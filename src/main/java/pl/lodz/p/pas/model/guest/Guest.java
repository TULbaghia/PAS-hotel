package pl.lodz.p.pas.model.guest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.lodz.p.pas.model.guest.exception.GuestException;
import pl.lodz.p.pas.model.guest.guesttype.BasicGuestType;
import pl.lodz.p.pas.model.guest.guesttype.GuestType;
import pl.lodz.p.pas.model.user.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@SessionScoped
@Named
@Getter
@ToString
public class Guest extends User implements Serializable {
    private int numberOfStays;
    private GuestType guestType;

    public Guest(String login, String firstname, String surname) {
        super(login, firstname, surname);
        this.guestType = new BasicGuestType();
        this.numberOfStays = 0;
    }

    public double getDiscount(double price) throws GuestException {
        return guestType.calculateDiscount(price);
    }

    // region GuestType
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
    // endregion


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return numberOfStays == guest.numberOfStays &&
                guestType.equals(guest.guestType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfStays, guestType);
    }
}
