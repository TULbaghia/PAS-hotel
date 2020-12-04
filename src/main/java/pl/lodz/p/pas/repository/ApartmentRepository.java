package pl.lodz.p.pas.repository;

import lombok.NonNull;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.repository.exception.RepositoryException;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApartmentRepository extends Repository<Apartment> {

    public Apartment get(int doorNumber) {
        return filter(x -> x.getDoorNumber() == doorNumber).stream().findFirst().orElse(null);
    }

    @Override
    public synchronized void add(@NonNull Apartment item) {
        if (get(item.getDoorNumber()) != null) {
            throw new RepositoryException("Door number already exists");
        }
        super.add(item);
    }

    @Override
    public synchronized void update(@NonNull Apartment item) {
        if(filter(x -> x.getDoorNumber() == item.getDoorNumber()).size() > 1) {
            throw new RepositoryException("Door number already exists");
        }
        super.update(item);
    }
}
