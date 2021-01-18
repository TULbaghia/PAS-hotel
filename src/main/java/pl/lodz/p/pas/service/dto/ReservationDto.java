package pl.lodz.p.pas.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import pl.lodz.p.pas.service.views.Views;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    @JsonProperty @NotEmpty
    @JsonView(Views.Public.class)
    private UUID id;
    @JsonProperty @NotEmpty
    @JsonView(Views.Public.class)
    private IdTraitDto guest;
    @JsonProperty @NotEmpty
    @JsonView(Views.Public.class)
    private IdTraitDto apartment;
    @JsonProperty @NotEmpty @Setter(AccessLevel.NONE)
    @JsonView(Views.Public.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private DateTime reservationStartDate;

    @JsonProperty
    public void setReservationStartDate(String text) {
        reservationStartDate = DateTime.parse(text, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @JsonProperty
    public void setId(String text) {
        id = UUID.fromString(text);
    }
}
