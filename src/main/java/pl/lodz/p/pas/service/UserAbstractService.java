package pl.lodz.p.pas.service;

import lombok.Getter;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.user.Admin;
import pl.lodz.p.pas.model.user.Manager;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.service.dto.Mapper;
import pl.lodz.p.pas.service.mapper.exception.ErrorProp;
import pl.lodz.p.pas.service.mapper.exception.RestException;
import pl.lodz.p.pas.service.views.Views;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
public class UserAbstractService <T extends User> {
    @Inject @Getter
    private UserManager userManager;

    private final T helpVariable;

    public UserAbstractService(T user) {
        helpVariable = user;
    }

    public String getAllUsers() {
        return new Mapper().writeAsString(Views.Public.class, new ArrayList<>(userManager.filter(x -> x.getClass().isInstance(helpVariable))));
    }

    public String getUser(String id, SecurityContext securityContext) {
        String currentUser = securityContext.getUserPrincipal().getName();
        User user;
        try {
            user = userManager.get(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            user = userManager.get(id);
        }
        User u = userManager.get(currentUser);
        if (((u instanceof Manager || u instanceof Admin) || currentUser.equals(user.getLogin()))
                && helpVariable.getClass().isInstance(user)) {
            return new Mapper().writeAsString(Views.Public.class, user);
        }
        return null;
    }

    public String addUser(User user) {
        userManager.add(user);
        return new Mapper().writeAsString(Views.Public.class, user);
    }

    public String updateUser(User user) {
        User editingUser = null;
        try {
            editingUser = (User) BeanUtils.cloneBean(userManager.get(user.getId()));
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RestException(Response.Status.PRECONDITION_FAILED, new ErrorProp("updateUser", "bean clone error"));
        }
        assert editingUser != null;
        editingUser.setLogin(user.getLogin());
        editingUser.setLastname(user.getLastname());
        editingUser.setPassword(user.getPassword());
        editingUser.setFirstname(user.getFirstname());
        userManager.update(editingUser);
        return new Mapper().writeAsString(Views.Public.class, userManager.get(user.getId()));
    }

    public String activateUser(User user) {
        User editingUser = null;
        try {
            editingUser = (User) BeanUtils.cloneBean(userManager.get(user.getId()));
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RestException(Response.Status.PRECONDITION_FAILED, new ErrorProp("updateUser", "bean clone error"));
        }
        assert editingUser != null;
        editingUser.setActive(user.isActive());
        userManager.update(editingUser);
        return new Mapper().writeAsString(Views.Public.class, userManager.get(user.getId()));
    }
}
