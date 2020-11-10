package pl.lodz.p.pas.model.apartment;

import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.lodz.p.pas.model.apartment.exception.ApartmentException;

import java.util.UUID;

@Getter
public class ThreeStarApartment extends Apartment {
    private String bonus;

    public ThreeStarApartment(UUID apartmentId, int howManyBeds, int doorNumber, double basePricePerDay, String bonus) throws ApartmentException {
        super(apartmentId, howManyBeds, doorNumber, basePricePerDay);
        this.bonus = bonus;
    }

    public ThreeStarApartment(int howManyBeds, int doorNumber, double basePricePerDay, String bonus) throws ApartmentException {
        super(howManyBeds, doorNumber, basePricePerDay);
        this.bonus = bonus;
    }


    @Override
    public double actualPricePerDay() {
        return 1.5 * super.getBasePricePerDay();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("bonus", bonus)
                .toString();
    }
}
