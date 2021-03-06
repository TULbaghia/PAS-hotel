package pl.lodz.p.pas.controller;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.manager.ReservationManager;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.exception.GuestException;
import pl.lodz.p.pas.model.exception.ReservationException;
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
import java.util.TimeZone;

@ApplicationScoped
public class FixtureLoader implements ServletContextListener {
    @Inject
    private UserManager userManager;

    @Inject
    private ReservationManager reservationManager;

    @Inject
    private ApartmentManager apartmentManager;

    private void initEagerly(@Observes @Initialized(ApplicationScoped.class) Object init) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Warsaw"));
        DateTimeZone.setDefault(DateTimeZone.forTimeZone(TimeZone.getDefault()));
        loadUserFixture();
        loadApartmentFixture();
        loadReservationFixture();
    }

    private void loadUserFixture() {
        userManager.add(Guest.builder().login("guest1").password("zaq1@WSX").firstname("Jan").lastname("Kowalski").build());
        userManager.add(Guest.builder().login("guest2").password("zaq1@WSX").firstname("Andrzej").lastname("Nowak").build());
        userManager.add(Guest.builder().login("guest3").password("zaq1@WSX").firstname("Jakub").lastname("Kowal").build());
        userManager.add(Guest.builder().login("guest4").password("zaq1@WSX").firstname("Kacper").lastname("Kowalczyk").build());
        userManager.add(Guest.builder().login("TestGuest").password("zaq1@WSX").firstname("TestGuest").lastname("TestGuest").build());

        userManager.add(Manager.builder().login("manager1").password("zaq1@WSX").firstname("Manager1").lastname("Kowalski").build());
        userManager.add(Manager.builder().login("manager2").password("zaq1@WSX").firstname("Manager2").lastname("Kowalski").build());
        userManager.add(Manager.builder().login("manager3").password("zaq1@WSX").firstname("Manager3").lastname("Kowalski").build());
        userManager.add(Manager.builder().login("manager4").password("zaq1@WSX").firstname("Manager4").lastname("Kowalski").build());
        userManager.add(Manager.builder().login("TestManager").password("zaq1@WSX").firstname("TestManager").lastname("TestManager").build());

        userManager.add(Admin.builder().login("admin1").password("zaq1@WSX").firstname("Admin1").lastname("Nowak").build());
        userManager.add(Admin.builder().login("admin2").password("zaq1@WSX").firstname("Admin2").lastname("Nowak").build());
        userManager.add(Admin.builder().login("admin3").password("zaq1@WSX").firstname("Admin3").lastname("Nowak").build());
        userManager.add(Admin.builder().login("admin4").password("zaq1@WSX").firstname("Admin4").lastname("Nowak").build());
        userManager.add(Admin.builder().login("TestAdmin").password("zaq1@WSX").firstname("TestAdmin").lastname("TestAdmin").build());
    }

    private void loadApartmentFixture() {
        apartmentManager.add(FiveStarApartment.builder().doorNumber(1).basePricePerDay(300).howManyBeds(3).bonus("pralka").pcName("pcAp1").build());
        apartmentManager.add(FiveStarApartment.builder().doorNumber(2).basePricePerDay(400).howManyBeds(4).bonus("lodowka").pcName("pcAp2").build());
        apartmentManager.add(FiveStarApartment.builder().doorNumber(3).basePricePerDay(500).howManyBeds(5).bonus("samochod").pcName("pcAp3").build());
        apartmentManager.add(FiveStarApartment.builder().doorNumber(4).basePricePerDay(1500).howManyBeds(15).bonus("samochsod").pcName("pcAp4").build());

        apartmentManager.add(ThreeStarApartment.builder().doorNumber(101).basePricePerDay(200).howManyBeds(2).bonus("lampka nocna").build());
        apartmentManager.add(ThreeStarApartment.builder().doorNumber(102).basePricePerDay(100).howManyBeds(1).bonus("koc").build());
        apartmentManager.add(ThreeStarApartment.builder().doorNumber(103).basePricePerDay(50).howManyBeds(0).bonus("dywan").build());

    }

    private void loadReservationFixture() {
        reservationManager.add(Reservation.builder()
                .guest((Guest) userManager.get("guest1"))
                .apartment(apartmentManager.get(1))
                .reservationStartDate(DateTime.now().minusMinutes(30))
                .build());

        reservationManager.add(Reservation.builder()
                .guest((Guest) userManager.get("guest2"))
                .apartment(apartmentManager.get(2))
                .reservationStartDate(DateTime.now().minusMinutes(30))
                .build());

        reservationManager.add(Reservation.builder()
                .guest((Guest) userManager.get("guest3"))
                .apartment(apartmentManager.get(3))
                .reservationStartDate(DateTime.now().minusMinutes(30))
                .build());

        reservationManager.add(Reservation.builder()
                .guest((Guest) userManager.get("guest4"))
                .apartment(apartmentManager.get(101))
                .reservationStartDate(DateTime.now().minusMinutes(30))
                .build());

        reservationManager.add(Reservation.builder()
                .guest((Guest) userManager.get("guest1"))
                .apartment(apartmentManager.get(102))
                .reservationStartDate(DateTime.now().minusMinutes(30))
                .build());

        reservationManager.add(Reservation.builder()
                .guest((Guest) userManager.get("guest2"))
                .apartment(apartmentManager.get(103))
                .reservationStartDate(DateTime.now().minusMinutes(30))
                .build());

        try {
            reservationManager.endReservation(reservationManager.getApartmentReservations(apartmentManager.get(2)).get(0).getId());
            reservationManager.endReservation(reservationManager.getApartmentReservations(apartmentManager.get(3)).get(0).getId());
        } catch (ReservationException | GuestException ignored) {

        }

    }
}
