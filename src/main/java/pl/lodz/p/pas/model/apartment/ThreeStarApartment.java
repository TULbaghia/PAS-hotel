package pl.lodz.p.pas.model.apartment;

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
public class ThreeStarApartment extends Apartment implements Serializable {
    private String bonus;

    public ThreeStarApartment(String apartmentId, int howManyBeds, int doorNumber, double basePricePerDay, String bonus) {
        super(apartmentId, howManyBeds, doorNumber, basePricePerDay);
        this.bonus = bonus;
    }

    public ThreeStarApartment(int howManyBeds, int doorNumber, double basePricePerDay, String bonus) {
        super(howManyBeds, doorNumber, basePricePerDay);
        this.bonus = bonus;
    }

    @Override
    public double actualPricePerDay() {
        return 1.5 * super.getBasePricePerDay();
    }
}
