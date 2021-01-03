package pl.lodz.p.pas.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.pas.model.trait.IdTrait;

import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends IdTrait {
    @JsonProperty @NonNull @NotEmpty
    private String login;
    @JsonProperty @NonNull @NotEmpty
    private String password;
    @JsonProperty @NonNull @NotEmpty
    private String firstname;
    @JsonProperty @NonNull @NotEmpty
    private String lastname;
    @Builder.Default
    @JsonProperty
    private boolean isActive = true;
}
