package pl.lodz.p.pas.model.guest.guesttype;

import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.lodz.p.pas.model.guest.exception.GuestException;

@Getter
public abstract class GuestType {
    private final int maxApartmentsNumber;

    public GuestType(int maxApartmentsNumber) {
        this.maxApartmentsNumber = maxApartmentsNumber;
    }

    public abstract double calculateDiscount(double price) throws GuestException;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("maxApartmentsNumber", maxApartmentsNumber)
                .toString();
    }
}
