package pl.lodz.p.pas.model.apartment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class FiveStarApartment extends Apartment implements Serializable {
    private String bonus;
    private String pcName;

    public FiveStarApartment(String apartmentId, int howManyBeds, int doorNumber, double basePricePerDay, String bonus, String pcName) {
        super(apartmentId, howManyBeds, doorNumber, basePricePerDay);
        this.bonus = bonus;
        this.pcName = pcName;
    }

    public FiveStarApartment(int howManyBeds, int doorNumber, double basePricePerDay, String bonus, String pcName) {
        super(howManyBeds, doorNumber, basePricePerDay);
        this.bonus = bonus;
        this.pcName = pcName;
    }

    public double actualPricePerDay() {
        return 2 * super.getBasePricePerDay();
    }
}
