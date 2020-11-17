package pl.lodz.p.pas.model.reservation;

import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.lodz.p.pas.model.apartment.Apartment;
import pl.lodz.p.pas.model.guest.Guest;
import pl.lodz.p.pas.model.guest.exception.GuestException;
import pl.lodz.p.pas.model.reservation.exception.ReservationException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {

    private @Getter String rentId;
    private @Getter Apartment apartment;
    private @Getter Guest guest;
    private @Getter LocalDateTime reservationStartDate;
    private LocalDateTime reservationEndDate = null;
    private double price;
    private @Getter
    Boolean isEnded;

    public Reservation(String rentId, Apartment apartment, Guest guest, LocalDateTime reservationStartDate) throws ReservationException {
        if (apartment == null) {
            throw new ReservationException("Apartment is null.");
        }
        if (guest == null) {
            throw new ReservationException("Guest is null.");
        }
        if (reservationStartDate == null) {
            throw new ReservationException("Reservation start date is null.");
        }
        this.rentId = rentId;
        this.apartment = apartment;
        this.guest = guest;
        this.reservationStartDate = reservationStartDate;
        this.isEnded = false;
    }

    public Reservation(Apartment apartment, Guest guest, LocalDateTime reservationStartDate) throws ReservationException {
        this(UUID.randomUUID().toString(), apartment, guest, reservationStartDate);
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
            this.price = this.guest.getDiscount(this.getDuration() * this.apartment.actualPricePerDay());
        }
    }

    public long getDuration() {
        if (reservationEndDate != null) {
            long duration = Math.abs(Duration.between(reservationEndDate, reservationStartDate).toDays());
            return duration == 0 ? 1 : duration;
        } else return 0;
    }

    public double getPrice() {
        return isEnded ? price : 0;
    }

    @Override
    public String toString() {
        return "Reservation{" + "rentId=" + rentId + ", apartment=" + apartment + ", guest=" + guest + ", reservationStartDate=" + reservationStartDate + ", reservationEndDate=" + reservationEndDate + ", price=" + price + ", isEnded=" + isEnded + '}';
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        return new EqualsBuilder()
                .append(rentId, that.rentId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(rentId)
                .toHashCode();
    }
}
