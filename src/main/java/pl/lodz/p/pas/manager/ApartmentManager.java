package pl.lodz.p.pas.manager;

import lombok.NonNull;
import pl.lodz.p.pas.manager.exception.ManagerException;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.repository.ApartmentRepository;
import pl.lodz.p.pas.repository.ReservationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Named
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
        if(reservationRepository.getApartmentReservations(get(id), true).size() > 0) {
            throw new ManagerException("Cannot delete- apartment alredy in use");
        }
        apartmentRepository.delete(id);
    }

    public List<Apartment> filter(Predicate<Apartment> predicate) {
        return apartmentRepository.filter(predicate);
    }

}
