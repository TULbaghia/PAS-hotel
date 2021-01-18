package pl.lodz.p.pas.model.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import pl.lodz.p.pas.model.exception.GuestException;
import pl.lodz.p.pas.model.exception.ReservationException;
import pl.lodz.p.pas.model.trait.IdTrait;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.service.views.Views;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Reservation extends IdTrait {
    @JsonProperty @NotEmpty
    @JsonView(Views.Public.class)
    private Apartment apartment;
    @JsonProperty @NotEmpty
    @JsonView(Views.Public.class)
    private Guest guest;
    @JsonProperty @JsonView(Views.Public.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private DateTime reservationStartDate = DateTime.now();

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Setter(AccessLevel.NONE)
    private DateTime reservationEndDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Setter(AccessLevel.NONE)
    private double price;

    public void endReservation() throws ReservationException, GuestException {
        if (reservationEndDate == null) {
            DateTime reservationEnd = DateTime.now();
            if (reservationEnd.isBefore(this.getReservationStartDate())) {
                throw new ReservationException("reservationEndedBeforeStart");
            }
            if(this.apartment == null) {
                throw new ReservationException("apartmentIsNull");
            }
            this.reservationEndDate = new DateTime(reservationEnd);
            this.price = this.guest.getDiscount(this.getDurationDays() * this.apartment.actualPricePerDay());
            return;
        }
        throw new ReservationException("alreadyEnded");
    }

    public long getDurationDays() {
        if (reservationEndDate != null) {
            long duration = Math.abs(new Duration(reservationEndDate, reservationStartDate).getStandardDays());
            return duration == 0 ? 1 : duration;
        }
        return 0;
    }
}
