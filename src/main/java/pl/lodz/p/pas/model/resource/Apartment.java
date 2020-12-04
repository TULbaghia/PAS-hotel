package pl.lodz.p.pas.model.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.pas.model.trait.IdTrait;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class Apartment extends IdTrait {
    private int howManyBeds;
    private int doorNumber;
    private double basePricePerDay;

    public abstract double actualPricePerDay();
}
