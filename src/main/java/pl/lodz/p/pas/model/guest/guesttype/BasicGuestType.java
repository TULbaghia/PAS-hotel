package pl.lodz.p.pas.model.guest.guesttype;

import lombok.ToString;
import pl.lodz.p.pas.model.guest.exception.BasicGuestTypeException;

@ToString(callSuper = true)
public class BasicGuestType extends GuestType {

    public BasicGuestType(int maxApartmentsNumber) {
        super(maxApartmentsNumber);
    }

    public BasicGuestType() {
        super(2);
    }

    @Override
    public double calculateDiscount(double price) throws BasicGuestTypeException {
        if (price < 0) {
            throw new BasicGuestTypeException("Price is smaller than 0.");
        }
        return price;
    }
}
