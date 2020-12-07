package pl.lodz.p.pas.manager;

import lombok.NonNull;
import pl.lodz.p.pas.manager.exception.ManagerException;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.repository.ApartmentRepository;
import pl.lodz.p.pas.repository.ReservationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

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

}
