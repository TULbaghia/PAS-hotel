package pl.lodz.p.pas.repository;

import lombok.NonNull;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.exception.RepositoryException;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserRepository extends Repository<User> {

    public User get(String login) {
        return filter(x -> x.getLogin().equals(login)).stream().findFirst().orElse(null);
    }

    public List<User> getAll(boolean active) {
        return filter(x -> x.isActive() == active);
    }

    @Override
    public synchronized void add(@NonNull User item) {
        if (get(item.getLogin()) != null) {
            throw new RepositoryException("userAlreadyExists");
        }
        super.add(item);
    }

    @Override
    public synchronized void update(@NonNull User item) {
        if(filter(x -> x.getLogin().equals(item.getLogin()) && !x.getId().equals(item.getId())).size() > 0) {
            throw new RepositoryException("loginAlreadyExists");
        }
        super.update(item);
    }

    @Override
    public synchronized void delete(@NonNull UUID id) {
        throw new RepositoryException("cannotDeleteUser");
    }
}
