package pl.lodz.p.pas.repository;

import lombok.NonNull;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.model.resource.Reservation;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.repository.exception.RepositoryException;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReservationRepository extends Repository<Reservation> {

    @Override
    public synchronized void add(@NonNull Reservation item) {
        if (item.getGuest().getMaxApartmentsNumber() < getGuestReservations(item.getGuest(), true).size() + 1) {
            throw new RepositoryException("Maximum apartments for user reached");
        }
        if (getApartmentReservations(item.getApartment(), true).size() > 0) {
            throw new RepositoryException("Apartment already rented");
        }
        super.add(item);
    }

    //TODO: czy tutaj brakuje warunków?
    @Override
    public synchronized void update(@NonNull Reservation item) {
        super.update(item);
    }

    @Override
    public synchronized void delete(@NonNull UUID id) {
        if (get(id).getReservationEndDate() != null) {
            throw new RepositoryException("Cannot delete finished reservation");
        }
        super.delete(id);
    }

    public List<Reservation> getAll(boolean available) {
        return filter(x -> available == (x.getReservationEndDate() == null));
    }

    public List<Reservation> getGuestReservations(@NonNull Guest guest) {
        return filter(x -> guest.equals(x.getGuest()));
    }

    public List<Reservation> getGuestReservations(@NonNull Guest guest, boolean active) {
        return getGuestReservations(guest).stream().filter(x -> active == (x.getReservationEndDate() == null)).collect(Collectors.toList());
    }

    public List<Reservation> getApartmentReservations(@NonNull Apartment apartment) {
        return filter(x -> apartment.equals(x.getApartment()));
    }

    public List<Reservation> getApartmentReservations(@NonNull Apartment apartment, boolean active) {
        return getApartmentReservations(apartment).stream().filter(x -> active == (x.getReservationEndDate() == null)).collect(Collectors.toList());
    }
}
