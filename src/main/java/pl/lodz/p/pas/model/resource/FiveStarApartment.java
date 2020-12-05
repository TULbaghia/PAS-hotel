package pl.lodz.p.pas.model.resource;

import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FiveStarApartment extends Apartment {
    private String bonus;
    private String pcName;

    public double actualPricePerDay() {
        return 2 * super.getBasePricePerDay();
    }

    @Override
    public String presentYourself() {
        return super.presentYourself() +
                "bonus(" + getBonus() + ") " +
                "pcName(" + getPcName() + ") ";
    }
}
