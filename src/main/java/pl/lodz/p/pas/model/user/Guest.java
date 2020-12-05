package pl.lodz.p.pas.model.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.pas.model.exception.GuestException;
import pl.lodz.p.pas.model.user.guesttype.BasicGuestType;
import pl.lodz.p.pas.model.user.guesttype.GuestType;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Guest extends User {
    private int numberOfStays;

    @Setter(AccessLevel.NONE)
    @NonNull
    @Builder.Default
    private GuestType guestType = new BasicGuestType();

    public double getDiscount(double price) throws GuestException {
        return guestType.calculateDiscount(price);
    }

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
}
