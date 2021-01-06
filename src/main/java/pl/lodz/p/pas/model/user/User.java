package pl.lodz.p.pas.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.lodz.p.pas.model.trait.IdTrait;
import pl.lodz.p.pas.service.views.Views;

import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@RequiredArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends IdTrait {
    @JsonProperty @NotEmpty
    @JsonView(Views.Public.class)
    private String login;
    @JsonProperty @NotEmpty
    @JsonView({Views.Confidential.class, Views.Registration.class})
    private String password;
    @JsonProperty @NotEmpty
    @JsonView(Views.Public.class)
    private String firstname;
    @JsonProperty @NotEmpty
    @JsonView(Views.Public.class)
    private String lastname;
    @Builder.Default
    @JsonProperty
    @JsonView(Views.Public.class)
    private boolean active = true;
}
