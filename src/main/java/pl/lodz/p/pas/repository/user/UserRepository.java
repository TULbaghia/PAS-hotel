package pl.lodz.p.pas.repository.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.IRepository;

@ApplicationScoped
public class UserRepository implements IRepository<User> {

    private List<User> users = new ArrayList<>();
  
    @Override
    public List<User> getAll() {
        return users.stream().collect(Collectors.toList());
    }
    
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
            }
            return;
        }

        throw new IllegalArgumentException();
    }
    
    @PostConstruct
    private void initRepository() {
        users.add(new User("user1login", "uname1", "usurname1"));
        users.add(new User("user2login", "uname2", "usurname2"));
        users.add(new User("user3login", "uname3", "usurname3"));
        users.add(new User("user4login", "uname4", "usurname4"));
        System.out.println("PostConstruct");
        System.out.println(users);
    }

}
