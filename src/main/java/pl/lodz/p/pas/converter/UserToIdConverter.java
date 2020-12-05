package pl.lodz.p.pas.converter;

import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.UserRepository;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@Named
public class UserToIdConverter implements Converter<User>, Serializable {

    @Inject
    private UserRepository userRepository;

    @Override
    public User getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return userRepository.get(UUID.fromString(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, User user) {
        return user.getId().toString();
    }
}
