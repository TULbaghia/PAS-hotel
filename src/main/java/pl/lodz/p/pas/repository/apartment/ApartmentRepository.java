package pl.lodz.p.pas.repository.apartment;

import java.util.ArrayList;
import java.util.List;
import pl.lodz.p.pas.model.apartment.Apartment;
import pl.lodz.p.pas.model.apartment.FiveStarApartment;
import pl.lodz.p.pas.model.apartment.ThreeStarApartment;
import pl.lodz.p.pas.model.apartment.exception.ApartmentException;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.IRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApartmentRepository implements IRepository<Apartment> {

  private List<Apartment> apartments = new ArrayList<>();

    public ApartmentRepository() {
    }

    @Override
    public void add(Apartment item) {
        apartments.add(item);
    }

    @Override
    public Apartment get(String id) {
        for (Apartment ap : apartments) {
            if (ap.getApartmentId().equals(id)) {
                return ap;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<Apartment> getAll() {
       return apartments;
    }

    @Override
    public void update(String id, Apartment item) {
         for (Apartment ap : apartments) {
            if (ap.getApartmentId().equals(id)) {
                int index = apartments.indexOf(ap);
                apartments.set(index, item);
                return;
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public void delete(String id) {
        for (Apartment ap : apartments) {
            if (ap.getApartmentId().equals(id)) {
                apartments.remove(ap);
                return;
            }
        }

        throw new IllegalArgumentException();
    }

    @PostConstruct
    private void initRepository() {
        try {
            apartments.add(new FiveStarApartment(3, 1, 1500, "Pełna lodówka1", "X3001"));
            apartments.add(new FiveStarApartment(3, 2, 1500, "Pełna lodówka2", "X3002"));
            apartments.add(new FiveStarApartment(3, 3, 1500, "Pełna lodówka3", "X3003"));
            apartments.add(new ThreeStarApartment(1, 4, 500, "Pełna lodówka1" ));
            apartments.add(new ThreeStarApartment(1, 5, 500, "Pełna lodówka2" ));
            apartments.add(new ThreeStarApartment(1, 6, 500, "Pełna lodówka3"));
        } catch (ApartmentException e) {
            System.err.format("%s, %s", e.getCause(), e.getMessage());
        }
    }
}
