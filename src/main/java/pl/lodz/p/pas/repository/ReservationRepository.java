package pl.lodz.p.pas.repository;

import lombok.NonNull;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.model.resource.Reservation;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.repository.exception.RepositoryException;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReservationRepository extends Repository<Reservation> {

    @Override
    public synchronized void add(@NonNull Reservation item) {
        if (item.getGuest().getMaxApartmentsNumber() < getGuestReservations(item.getGuest(), true).size() + 1) {
            throw new RepositoryException("maximumApartmentsReached");
        }
        if (getApartmentReservations(item.getApartment(), true).size() > 0) {
            throw new RepositoryException("apartmentAlreadyRented");
        }
        if (!item.getGuest().isActive()) {
            throw new RepositoryException("guestIsNotActive");
        }
        if (!item.getReservationStartDate().isAfter(LocalDateTime.now().minusHours(1))) {
            throw new RepositoryException("incorrectReservationStartDate");
        }
        super.add(item);
    }

    @Override
    public synchronized void update(@NonNull Reservation item) {
        if (item.getGuest().getMaxApartmentsNumber() <= getGuestReservations(item.getGuest(), true).stream().filter(x -> !x.getId().equals(item.getId())).count()) {
            throw new RepositoryException("maximumApartmentsReached");
        }
        if (!item.getGuest().isActive()) {
            throw new RepositoryException("guestIsNotActive");
        }
        if(get(item.getId()) == null) {
            throw new RepositoryException("reservationDoesNotExist");
        }
        if (get(item.getId()).getReservationEndDate() != null) {
            throw new RepositoryException("reservationHasEnded");
        }
        super.update(item);
    }

    @Override
    public synchronized void delete(@NonNull UUID id) {
        Reservation r = get(id);
        if (r == null) {
            throw new RepositoryException("reservationDoesNotExist");
        }
        if (r.getReservationEndDate() != null) {
            throw new RepositoryException("cannotDeleteFinishedReservation");
        }
        super.delete(id);
    }

    public List<Reservation> getAll(boolean isNotFinished) {
        return filter(x -> isNotFinished == (x.getReservationEndDate() == null));
    }

    public List<Reservation> getGuestReservations(@NonNull Guest guest) {
        return filter(x -> guest.equals(x.getGuest()));
    }

    public List<Reservation> getGuestReservations(@NonNull Guest guest, boolean isNotFinished) {
        return getGuestReservations(guest).stream().filter(x -> isNotFinished == (x.getReservationEndDate() == null)).collect(Collectors.toList());
    }

    public List<Reservation> getApartmentReservations(@NonNull Apartment apartment) {
        return filter(x -> apartment.equals(x.getApartment()));
    }

    public List<Reservation> getApartmentReservations(@NonNull Apartment apartment, boolean isNotFinished) {
        return getApartmentReservations(apartment).stream().filter(x -> isNotFinished == (x.getReservationEndDate() == null)).collect(Collectors.toList());
    }
}
