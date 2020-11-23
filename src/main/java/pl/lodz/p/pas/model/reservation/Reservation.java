package pl.lodz.p.pas.model.reservation;

import lombok.*;
import pl.lodz.p.pas.model.apartment.Apartment;
import pl.lodz.p.pas.model.guest.Guest;
import pl.lodz.p.pas.model.guest.exception.GuestException;
import pl.lodz.p.pas.model.reservation.exception.ReservationException;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@SessionScoped
@Named
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Reservation implements Serializable {

    @Setter @NonNull private String rentId;
    @Setter @NonNull private Apartment apartment;
    @Setter @NonNull private Guest guest;
    @Setter @NonNull private LocalDateTime reservationStartDate;
    private LocalDateTime reservationEndDate = null;
    private double price;
    private boolean isEnded;

    public Reservation(Apartment apartment, Guest guest, LocalDateTime reservationStartDate) {
        this(UUID.randomUUID().toString(), apartment, guest, reservationStartDate);
    }

    public Reservation() {
        this.rentId = UUID.randomUUID().toString();
    }

    public void endReservation() throws ReservationException, GuestException {
        this.isEnded = true;
        LocalDateTime reservationEnd= LocalDateTime.now();
        if (reservationEnd.isBefore(this.getReservationStartDate())) {
            throw new ReservationException("Reservation ended before start date.");
        }
        this.reservationEndDate = reservationEnd;
        setEndedReservationPrice();
    }

    private void setEndedReservationPrice() throws GuestException {
        if (isEnded) {
            this.price = this.guest.getDiscount(this.getDurationDays() * this.apartment.actualPricePerDay());
        }
    }

    public long getDurationDays() {
        if (reservationEndDate != null) {
            long duration = Math.abs(Duration.between(reservationEndDate, reservationStartDate).toDays());
            return duration == 0 ? 1 : duration;
        }
        return 0;
    }
}
