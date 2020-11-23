package pl.lodz.p.pas.model.guest.guesttype;

import lombok.ToString;
import pl.lodz.p.pas.model.guest.exception.SpecialGuestTypeException;

@ToString(callSuper = true)
public class SpecialGuestType extends GuestType {

    public SpecialGuestType(int maxApartmentsNumber) {
        super(maxApartmentsNumber);
    }

    public SpecialGuestType() {
        super(4);
    }

    @Override
    public double calculateDiscount(double price) throws SpecialGuestTypeException {
        if (price < 0) {
            throw new SpecialGuestTypeException("Price is smaller than 0.");
        }
        return (int) (.8 * price);
    }
}
