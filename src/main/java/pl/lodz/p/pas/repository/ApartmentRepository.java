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
            throw new RepositoryException("doorNumberAlreadyExist");
        }
        super.add(item);
    }

    @Override
    public synchronized void update(@NonNull Apartment item) {
        if(filter(x -> x.getDoorNumber() == item.getDoorNumber() && !x.getId().equals(item.getId())).size() > 0) {
            throw new RepositoryException("doorNumberAlreadyExist");
        }
        super.update(item);
    }
}
