package pl.lodz.p.pas.model.user.guesttype;

import lombok.ToString;
import pl.lodz.p.pas.model.exception.GuestTypeException;

@ToString(callSuper = true)
public class BasicGuestType extends GuestType {

    public BasicGuestType() {
        super(2);
    }

    public BasicGuestType(int maxApartmentsNumber) {
        super(maxApartmentsNumber);
    }

    @Override
    public double calculateDiscount(double price) throws GuestTypeException {
        if (price < 0) {
            throw new GuestTypeException("priceSmallerThanZero");
        }
        return price;
    }
}
