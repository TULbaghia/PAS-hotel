package pl.lodz.p.pas.manager;

import lombok.NonNull;
import pl.lodz.p.pas.manager.exception.ManagerException;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.model.resource.Reservation;
import pl.lodz.p.pas.repository.ApartmentRepository;
import pl.lodz.p.pas.repository.ReservationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
public class ApartmentManager {
    @Inject
    private ReservationRepository reservationRepository;

    @Inject
    private ApartmentRepository apartmentRepository;

    public void add(@NonNull Apartment apartment) {
        apartmentRepository.add(apartment);
    }

    public Apartment get(UUID id) {
        return apartmentRepository.get(id);
    }

    public Apartment get(int doorNumber) {
        return apartmentRepository.get(doorNumber);
    }

    public List<Apartment> getAll() {
        return apartmentRepository.getAll();
    }

    public void update(@NonNull Apartment apartment) {
        apartmentRepository.update(apartment);
    }

    public void delete(@NonNull UUID id) {
        Apartment a = apartmentRepository.get(id);
        if (a == null) {
            throw new ManagerException("apartmentDoesNotExist");
        }
        if(reservationRepository.getApartmentReservations(get(id), true).size() > 0) {
            throw new ManagerException("cannotDeleteApartmentInUse");
        }
        apartmentRepository.delete(id);
        reservationRepository.filter(x -> a.equals(x.getApartment())).forEach(x -> x.setApartment(null));
    }

    public List<Apartment> filter(Predicate<Apartment> predicate) {
        return apartmentRepository.filter(predicate);
    }

    public List<Apartment> paginate(int itemsPerPage, int page, Predicate<Apartment> p) {
        return apartmentRepository.filter(p).stream().skip((page-1)*itemsPerPage).limit(itemsPerPage).collect(Collectors.toList());
    }
}
