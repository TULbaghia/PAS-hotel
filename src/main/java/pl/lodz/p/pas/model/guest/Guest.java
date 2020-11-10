package pl.lodz.p.pas.model.guest;

import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.lodz.p.pas.model.guest.exception.GuestException;
import pl.lodz.p.pas.model.guest.guesttype.BasicGuestType;
import pl.lodz.p.pas.model.guest.guesttype.GuestType;

import java.util.UUID;

@Getter
public class Guest {
    private final UUID guestId;
    private final String firstName;
    private final String surname;
    private final String address;
    private final int numberOfStays;
    private GuestType guestType;

    public Guest(UUID guestId, String firstName, String surname, String address) throws GuestException {
        if (firstName.matches("")) {
            throw new GuestException("Empty firstname.");
        }
        if (surname.matches("")) {
            throw new GuestException("Empty surname.");
        }
        if (address.matches("")) {
            throw new GuestException("Empty address.");
        }

        this.guestId = guestId;
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.guestType = new BasicGuestType();
        this.numberOfStays = 0;
    }

    public Guest(String firstName, String surname, String address) throws GuestException {
        this(UUID.randomUUID(), firstName, surname, address);
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
    public String toString() {
        return new ToStringBuilder(this)
                .append("guestId", guestId)
                .append("firstName", firstName)
                .append("surName", surname)
                .append("address", address)
                .append("numberOfStays", numberOfStays)
                .append("guestType", guestType)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Guest guest = (Guest) o;

        return new EqualsBuilder()
                .append(guestId, guest.guestId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(guestId)
                .toHashCode();
    }
}
