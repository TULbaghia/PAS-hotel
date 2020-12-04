package pl.lodz.p.pas.model.user.guesttype;

import lombok.ToString;
import pl.lodz.p.pas.model.exception.GuestTypeException;

@ToString(callSuper = true)
public class SpecialGuestType extends GuestType {

    public SpecialGuestType() {
        super(4);
    }

    public SpecialGuestType(int maxApartmentsNumber) {
        super(maxApartmentsNumber);
    }

    @Override
    public double calculateDiscount(double price) throws GuestTypeException {
        if (price < 0) {
            throw new GuestTypeException("Price is smaller than 0.");
        }
        return .8 * price;
    }
}
