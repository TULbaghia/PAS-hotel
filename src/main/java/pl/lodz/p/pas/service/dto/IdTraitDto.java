package pl.lodz.p.pas.service.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.pas.model.trait.IdTrait;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IdTraitDto extends IdTrait {
    public IdTraitDto(String uuid) {
        super.setId(UUID.fromString(uuid));
    }
}
