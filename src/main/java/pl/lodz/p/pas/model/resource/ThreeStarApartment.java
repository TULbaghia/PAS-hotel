package pl.lodz.p.pas.model.resource;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ThreeStarApartment extends Apartment {
    private String bonus;

    @Override
    public double actualPricePerDay() {
        return 1.5 * super.getBasePricePerDay();
    }
}
