package pl.lodz.p.pas.model.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.pas.model.trait.IdTrait;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends IdTrait {
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    @Builder.Default
    private boolean isActive = true;
}
