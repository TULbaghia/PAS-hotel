package pl.lodz.p.pas.manager;

import lombok.NonNull;
import pl.lodz.p.pas.manager.exception.ManagerException;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

@ApplicationScoped
@Named
public class UserManager implements Serializable {

    @Inject
    private HttpServletRequest request;

    @Inject
    private UserRepository userRepository;

    public void add(@NonNull User user) {
        userRepository.add(user);
    }

    public User get(UUID id) {
        return userRepository.get(id);
    }

    public User get(String login) {
        return userRepository.get(login);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public List<User> getAll(boolean active) {
        return userRepository.getAll(active);
    }

    public void update(@NonNull User user) {
        userRepository.update(user);
    }

    public List<User> filter(Predicate<User> predicate) {
        return userRepository.filter(predicate);
    }

    public void activateUser(@NonNull User user) {
        get(user.getId()).setActive(true);
    }

    public void deactivateUser(@NonNull User user) {
        get(user.getId()).setActive(false);
    }

    public User getCurrentUser() {
        User u = get(request.getRemoteUser());
        if(u == null) {
            throw new ManagerException("usermanager_not_logged_in");
        }
        return u;
    }
}
