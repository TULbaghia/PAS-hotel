package pl.lodz.p.pas.repository.user;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.IRepository;

@ApplicationScoped
public class UserRepository implements IRepository<User> {

    private List<User> users = new ArrayList<>();

    @Override
    public void add(User item) {
        users.add(item);
    }

    @Override
    public User get(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void update(String id, User item) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                int index = users.indexOf(user);
                users.set(index, item);
                return;
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public void delete(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                users.remove(user);
                return;
            }
        }

        throw new IllegalArgumentException();
    }

    @PostConstruct
    private void initRepository() {
        users.add(new User("user1l", "user1n", "user1s"));
        users.add(new User("user2l", "user2n", "user2s"));
        users.add(new User("user3l", "user3n", "user3s"));
        users.add(new User("user4l", "user4n", "user4s"));
    }

}
