package pl.lodz.p.pas.model.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.pas.model.trait.IdTrait;
import pl.lodz.p.pas.service.views.Views;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class Apartment extends IdTrait {
    @JsonProperty
    @JsonView(Views.Public.class)
    @PositiveOrZero
    private int howManyBeds;
    @JsonProperty
    @JsonView(Views.Public.class)
    @PositiveOrZero
    private int doorNumber;
    @JsonProperty
    @JsonView(Views.Public.class)
    @Positive
    private double basePricePerDay;

    public abstract double actualPricePerDay();

    public String presentYourself() {
        return "[" + getId() + "] DoorN(" + getDoorNumber() + ") Price(" + getBasePricePerDay() + ") ";
    }
}
