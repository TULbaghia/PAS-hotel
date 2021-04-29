package pl.lodz.p.pas.model.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.pas.service.views.Views;

import javax.validation.constraints.NotEmpty;


@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ThreeStarApartment extends Apartment {
    @JsonProperty @NotEmpty
    @JsonView(Views.Public.class)
    private String bonus;

    @Override
    public double actualPricePerDay() {
        return 1.5 * super.getBasePricePerDay();
    }

    @Override
    public String presentYourself() {
        return super.presentYourself() + "bonus(" + getBonus() + ") ";
    }
}
