package pl.lodz.p.pas.controller;

import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.manager.ReservationManager;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.model.resource.FiveStarApartment;
import pl.lodz.p.pas.model.resource.ThreeStarApartment;
import pl.lodz.p.pas.model.user.Admin;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.model.user.Manager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContextListener;

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

        userManager.add(Manager.builder().login("manager1").password("manager1").firstname("Manager").lastname("Kowalski").build());

        userManager.add(Admin.builder().login("admin1").password("admin1").firstname("Admin").lastname("Nowak").build());
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
    }
}
