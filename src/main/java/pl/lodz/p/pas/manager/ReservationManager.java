package pl.lodz.p.pas.manager;

import lombok.NonNull;
import pl.lodz.p.pas.manager.exception.ManagerException;
import pl.lodz.p.pas.model.exception.GuestException;
import pl.lodz.p.pas.model.exception.ReservationException;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.model.resource.Reservation;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.model.user.guesttype.BasicGuestType;
import pl.lodz.p.pas.model.user.guesttype.SpecialGuestType;
import pl.lodz.p.pas.repository.ReservationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@ApplicationScoped
public class ReservationManager {
    @Inject
    private ReservationRepository reservationRepository;

    public void add(@NonNull Reservation reservation) {
        reservationRepository.add(reservation);
    }

    public void endReservation(@NonNull UUID id) throws ReservationException, GuestException {
        Reservation r = reservationRepository.get(id);
        if(r == null) {
            throw new ManagerException("reservationDoesNotExist");
        }
        r.endReservation();
        double guestSpentSum = getGuestReservations(r.getGuest(), false).stream().mapToDouble(Reservation::getPrice).sum();

        if(guestSpentSum > 10000d && r.getGuest().getGuestType() instanceof BasicGuestType) {
            r.getGuest().changeGuestType(new SpecialGuestType());
        }
    }

    public Reservation get(UUID id) {
        return reservationRepository.get(id);
    }

    public List<Reservation> getAll() {
        return reservationRepository.getAll();
    }

    public List<Reservation> getAll(boolean isNotFinished) {
        return reservationRepository.getAll(isNotFinished);
    }

    public List<Reservation> getGuestReservations(@NonNull Guest guest) {
        return reservationRepository.getGuestReservations(guest);
    }

    public List<Reservation> getGuestReservations(@NonNull Guest guest, boolean isNotFinished) {
        return reservationRepository.getGuestReservations(guest, isNotFinished);
    }

    public List<Reservation> getApartmentReservations(@NonNull Apartment apartment) {
        return reservationRepository.getApartmentReservations(apartment);
    }

    public List<Reservation> getApartmentReservations(@NonNull Apartment apartment, boolean isNotFinished) {
        return reservationRepository.getApartmentReservations(apartment, isNotFinished);
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

    public List<Reservation> filterByResources(String... args) {
        return reservationRepository.filter(x -> x.getGuest().toString().contains(args[0])
                && (x.getApartment() == null || x.getApartment().toString().contains(args[1]))
                && x.toString().contains(args[2]));
    }

    public List<Reservation> filterByResources(User user, String... args) {
        return reservationRepository.filter(x -> x.getGuest().getId().equals(user.getId()) && x.getGuest().toString().contains(args[0])
                && (x.getApartment() == null || x.getApartment().toString().contains(args[1]))
                && x.toString().contains(args[2]));
    }

}
