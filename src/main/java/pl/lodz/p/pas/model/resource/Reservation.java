package pl.lodz.p.pas.model.resource;

import lombok.*;
import pl.lodz.p.pas.model.exception.GuestException;
import pl.lodz.p.pas.model.exception.ReservationException;
import pl.lodz.p.pas.model.trait.IdTrait;
import pl.lodz.p.pas.model.user.Guest;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Reservation extends IdTrait {
    private Apartment apartment;
    private Guest guest;
    private LocalDateTime reservationStartDate = LocalDateTime.now();

    @Setter(AccessLevel.NONE)
    private LocalDateTime reservationEndDate;

    @Setter(AccessLevel.NONE)
    private double price;

    public void endReservation() throws ReservationException, GuestException {
        if (reservationEndDate == null) {
            LocalDateTime reservationEnd = LocalDateTime.now();
            if (reservationEnd.isBefore(this.getReservationStartDate())) {
                throw new ReservationException("Reservation ended before start date.");
            }
            if(this.apartment == null) {
                throw new ReservationException("Cannot finish reservation due to null Apartment");
            }
            this.reservationEndDate = reservationEnd;
            this.price = this.guest.getDiscount(this.getDurationDays() * this.apartment.actualPricePerDay());
            return;
        }
        throw new ReservationException("Reservation already ended");
    }

    public long getDurationDays() {
        if (reservationEndDate != null) {
            long duration = Math.abs(Duration.between(reservationEndDate, reservationStartDate).toDays());
            return duration == 0 ? 1 : duration;
        }
        return 0;
    }
}
