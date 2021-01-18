package pl.lodz.p.pas.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.pas.model.trait.IdTrait;
import pl.lodz.p.pas.service.views.Views;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    @JsonProperty @NotEmpty
    @JsonView(Views.Public.class)
    private IdTraitDto id;
    @JsonProperty @NotEmpty
    @JsonView(Views.Public.class)
    private IdTraitDto guest;
    @JsonProperty @NotEmpty
    @JsonView(Views.Public.class)
    private IdTraitDto apartment;
}
