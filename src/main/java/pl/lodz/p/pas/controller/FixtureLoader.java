package pl.lodz.p.pas.controller;

import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.manager.ReservationManager;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.exception.GuestException;
import pl.lodz.p.pas.model.exception.ReservationException;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.model.resource.FiveStarApartment;
import pl.lodz.p.pas.model.resource.Reservation;
import pl.lodz.p.pas.model.resource.ThreeStarApartment;
import pl.lodz.p.pas.model.user.Admin;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.model.user.Manager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContextListener;
import java.time.LocalDateTime;

@ApplicationScoped
public class FixtureLoader implements ServletContextListener {
    @Inject
    private UserManager userManager;

    @Inject
    private ReservationManager reservationManager;

    @Inject
    private ApartmentManager apartmentManager;

    private void initEagerly(@Observes @Initialized(ApplicationScoped.class) Object init) {
        loadUserFixture();
        loadApartmentFixture();
        loadReservationFixture();
    }

    private void loadUserFixture() {
        userManager.add(Guest.builder().login("guest1").password("guest1").firstname("Jan").lastname("Kowalski").build());
        userManager.add(Guest.builder().login("guest2").password("guest2").firstname("Andrzej").lastname("Nowak").build());
        userManager.add(Guest.builder().login("guest3").password("guest3").firstname("Jakub").lastname("Kowal").build());
        userManager.add(Guest.builder().login("guest4").password("guest4").firstname("Kacper").lastname("Kowalczyk").build());
        userManager.add(Guest.builder().login("TestGuest").password("12345").firstname("TestGuest").lastname("TestGuest").build());

        userManager.add(Manager.builder().login("manager1").password("manager1").firstname("Manager").lastname("Kowalski").build());
        userManager.add(Manager.builder().login("TestManager").password("12345").firstname("TestManager").lastname("TestManager").build());

        userManager.add(Admin.builder().login("admin1").password("admin1").firstname("Admin").lastname("Nowak").build());
        userManager.add(Admin.builder().login("TestAdmin").password("12345").firstname("TestAdmin").lastname("TestAdmin").build());
    }

    private void loadApartmentFixture() {
        apartmentManager.add(FiveStarApartment.builder().doorNumber(1).basePricePerDay(300).howManyBeds(3).bonus("pralka").pcName("pcAp1").build());
        apartmentManager.add(FiveStarApartment.builder().doorNumber(2).basePricePerDay(400).howManyBeds(4).bonus("lodowka").pcName("pcAp2").build());
        apartmentManager.add(FiveStarApartment.builder().doorNumber(3).basePricePerDay(500).howManyBeds(5).bonus("samochod").pcName("pcAp3").build());

        apartmentManager.add(ThreeStarApartment.builder().doorNumber(101).basePricePerDay(200).howManyBeds(2).bonus("lampka nocna").build());
        apartmentManager.add(ThreeStarApartment.builder().doorNumber(102).basePricePerDay(100).howManyBeds(1).bonus("koc").build());
        apartmentManager.add(ThreeStarApartment.builder().doorNumber(103).basePricePerDay(50).howManyBeds(0).bonus("dywan").build());
    }

    private void loadReservationFixture() {
        reservationManager.add(Reservation.builder()
                .guest((Guest) userManager.get("guest1"))
                .apartment(apartmentManager.get(1))
                .reservationStartDate(LocalDateTime.now().minusMinutes(30))
                .build());

        reservationManager.add(Reservation.builder()
                .guest((Guest) userManager.get("guest2"))
                .apartment(apartmentManager.get(2))
                .reservationStartDate(LocalDateTime.now().minusMinutes(30))
                .build());

        reservationManager.add(Reservation.builder()
                .guest((Guest) userManager.get("guest3"))
                .apartment(apartmentManager.get(3))
                .reservationStartDate(LocalDateTime.now().minusMinutes(30))
                .build());

        reservationManager.add(Reservation.builder()
                .guest((Guest) userManager.get("guest4"))
                .apartment(apartmentManager.get(101))
                .reservationStartDate(LocalDateTime.now().minusMinutes(30))
                .build());

        try {
            reservationManager.endReservation(reservationManager.getApartmentReservations(apartmentManager.get(2)).get(0).getId());
            reservationManager.endReservation(reservationManager.getApartmentReservations(apartmentManager.get(3)).get(0).getId());
        } catch (ReservationException | GuestException ignored) {

        }

    }
}
