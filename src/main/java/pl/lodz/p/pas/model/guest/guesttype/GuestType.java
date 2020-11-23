package pl.lodz.p.pas.model.guest.guesttype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pl.lodz.p.pas.model.guest.exception.GuestException;

@Getter
@AllArgsConstructor
@ToString
public abstract class GuestType {
    private final int maxApartmentsNumber;

    public abstract double calculateDiscount(double price) throws GuestException;
}
