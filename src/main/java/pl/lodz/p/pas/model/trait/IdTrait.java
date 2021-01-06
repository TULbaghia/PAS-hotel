package pl.lodz.p.pas.model.trait;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.pas.service.views.Views;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class IdTrait {
    @JsonView(Views.Public.class)
    private UUID id;
}
