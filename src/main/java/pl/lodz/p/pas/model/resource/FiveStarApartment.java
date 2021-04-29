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
public class FiveStarApartment extends Apartment {
    @JsonProperty @NotEmpty
    @JsonView(Views.Public.class)
    private String bonus;
    @JsonProperty @NotEmpty
    @JsonView(Views.Public.class)
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
