package pl.lodz.p.pas.model.trait;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class IdTrait {
    private UUID id;
}
