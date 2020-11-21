package pl.lodz.p.pas.model.apartment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.lodz.p.pas.model.apartment.exception.ApartmentException;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@SessionScoped
@Named
@Getter
@NoArgsConstructor
public class FiveStarApartment extends Apartment implements Serializable {
    private String bonus;
    private String pcName;

    public FiveStarApartment(String apartmentId, int howManyBeds, int doorNumber, double basePricePerDay, String bonus, String pcName) throws ApartmentException {
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
