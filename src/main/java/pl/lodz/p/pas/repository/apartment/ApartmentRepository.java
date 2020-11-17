package pl.lodz.p.pas.repository.apartment;

import java.util.ArrayList;
import java.util.List;
import pl.lodz.p.pas.model.apartment.Apartment;
import pl.lodz.p.pas.repository.IRepository;

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
            }
            return;
        }

        throw new IllegalArgumentException();
    }
}
