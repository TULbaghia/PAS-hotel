package pl.lodz.p.pas.model.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.pas.model.apartment.Apartment;
import pl.lodz.p.pas.model.apartment.FiveStarApartment;
import pl.lodz.p.pas.model.apartment.ThreeStarApartment;
import pl.lodz.p.pas.model.apartment.exception.ApartmentException;
import pl.lodz.p.pas.model.guest.Guest;
import pl.lodz.p.pas.model.guest.exception.GuestException;
import pl.lodz.p.pas.model.guest.guesttype.SpecialGuestType;
import pl.lodz.p.pas.model.reservation.exception.ReservationException;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationTest {

    Guest guest;
    LocalDateTime localDateTime;
    Apartment apartment;

    @BeforeEach
    public void setUp() {
        guest = new Guest("Adam", "Kowalski", "Bezowa");
        localDateTime = LocalDateTime.of(2020, 10, 19, 15, 15);
    }

    @Test
    public void reservationBasicGuestTestCase() throws ReservationException, GuestException {
        apartment = new FiveStarApartment(1, 23, 32, "Darmowe napoje", "Komputer_23");
        Reservation reservation = new Reservation(apartment, guest, localDateTime);

        assertEquals(reservation.getApartment(), apartment);
        assertEquals(reservation.getGuest(), guest);
        assertEquals(reservation.getPrice(), 0);

        reservation.endReservation();

        assertEquals(reservation.getApartment(), apartment);
        assertEquals(reservation.getGuest(), guest);
        assertEquals(reservation.getDurationDays(), getDays());
        assertEquals(reservation.getPrice(), getDiscount(reservation));
    }

    @Test
    public void reservationSpecialGuestTestCase() throws GuestException, ReservationException {
        guest.changeGuestType(new SpecialGuestType());
        apartment = new ThreeStarApartment(1, 23, 32, "Darmowe napoje");
        Reservation reservation = new Reservation(apartment, guest, localDateTime);

        assertEquals(reservation.getApartment(), apartment);
        assertEquals(reservation.getGuest(), guest);
        assertEquals(reservation.getPrice(), 0);

        reservation.endReservation();

        assertEquals(reservation.getApartment(), apartment);
        assertEquals(reservation.getGuest(), guest);
        assertEquals(reservation.getDurationDays(), getDays());
        assertEquals(reservation.getPrice(), getDiscount(reservation));

    }

    private double getDiscount(Reservation reservation) throws GuestException {
        return guest.getDiscount((reservation.getDurationDays() * apartment.actualPricePerDay()));
    }

    private long getDays() {
        return Math.abs(Duration.between(LocalDateTime.now(), localDateTime).toDays());
    }
}
