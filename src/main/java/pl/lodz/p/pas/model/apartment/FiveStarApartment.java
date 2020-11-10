package pl.lodz.p.pas.model.apartment;

import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.lodz.p.pas.model.apartment.exception.ApartmentException;

import java.util.UUID;

@Getter
public class FiveStarApartment extends Apartment {
    private String bonus;
    private String pcName;

    public FiveStarApartment(UUID apartmentId, int howManyBeds, int doorNumber, double basePricePerDay, String bonus, String pcName) throws ApartmentException {
        super(apartmentId, howManyBeds, doorNumber, basePricePerDay);
        this.bonus = bonus;
        this.pcName = pcName;
    }

    public FiveStarApartment(int howManyBeds, int doorNumber, double basePricePerDay, String bonus, String pcName) throws ApartmentException {
        super(howManyBeds, doorNumber, basePricePerDay);
        this.bonus = bonus;
        this.pcName = pcName;
    }

    public double actualPricePerDay() {
        return 2 * super.getBasePricePerDay();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("bonus", bonus)
                .append("pcName", pcName)
                .toString();
    }


}
