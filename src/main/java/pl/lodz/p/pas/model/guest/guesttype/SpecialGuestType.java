package pl.lodz.p.pas.model.guest.guesttype;

import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.lodz.p.pas.model.guest.exception.SpecialGuestTypeException;

public class SpecialGuestType extends GuestType {
    public SpecialGuestType(int maxApartmentsNumber) {
        super(maxApartmentsNumber);
    }

    public SpecialGuestType() {
        super(4);
    }

    @Override
    public double calculateDiscount(double price) throws SpecialGuestTypeException {
        if(price < 0) {
            throw new SpecialGuestTypeException("Price is smaller than 0.");
        } else return (int) (.8 * price);

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
