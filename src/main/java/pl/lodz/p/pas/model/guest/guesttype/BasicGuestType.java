package pl.lodz.p.pas.model.guest.guesttype;

import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.lodz.p.pas.model.guest.exception.BasicGuestTypeException;

public class BasicGuestType extends GuestType {
    public BasicGuestType(int maxApartmentsNumber) {
        super(maxApartmentsNumber);
    }

    public BasicGuestType() {
        super(2);
    }

    @Override
    public double calculateDiscount(double price) throws BasicGuestTypeException {
        if(price < 0) {
            throw new BasicGuestTypeException("Price is smaller than 0.");
        } else return price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .toString();
    }
}
