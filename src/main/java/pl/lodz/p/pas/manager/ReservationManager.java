package pl.lodz.p.pas.manager;

import lombok.NonNull;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.model.resource.Reservation;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.repository.ReservationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Named
@ApplicationScoped
public class ReservationManager {
    @Inject
    private ReservationRepository reservationRepository;

    public void add(@NonNull Reservation reservation) {
        reservationRepository.add(reservation);
    }

    public Reservation get(UUID id) {
        return reservationRepository.get(id);
    }

    public List<Reservation> getAll() {
        return reservationRepository.getAll();
    }

    public List<Reservation> getAll(boolean available) {
        return reservationRepository.getAll(available);
    }

    public List<Reservation> getGuestReservations(@NonNull Guest guest) {
        return reservationRepository.getGuestReservations(guest);
    }

    public List<Reservation> getGuestReservations(@NonNull Guest guest, boolean active) {
        return reservationRepository.getGuestReservations(guest,active);
    }

    public List<Reservation> getApartmentReservations(@NonNull Apartment apartment) {
        return reservationRepository.getApartmentReservations(apartment);
    }

    public List<Reservation> getApartmentReservations(@NonNull Apartment apartment, boolean active) {
        return reservationRepository.getApartmentReservations(apartment, active);
    }

    public void update(@NonNull Reservation reservation) {
        reservationRepository.update(reservation);
    }

    public void delete(@NonNull UUID id) {
        reservationRepository.delete(id);
    }

    public List<Reservation> filter(Predicate<Reservation> predicate) {
        return reservationRepository.filter(predicate);
    }

}
