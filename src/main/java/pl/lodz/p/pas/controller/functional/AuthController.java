package pl.lodz.p.pas.controller.functional;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.manager.exception.ManagerException;
import pl.lodz.p.pas.model.user.User;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@RequestScoped
@Named
public class AuthController implements Serializable {

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

    @Inject
    private HttpServletRequest request;

    @Inject
    private UserManager userManager;

    public String login() {
        User u = userManager.get(username);
        try {
            if(u == null) {
                throw new ManagerException("auth_incorrect_data");
            }
            request.login(username, password);
            return "Index";
        } catch (ManagerException | ServletException e) {
            FacesContext.getCurrentInstance().addMessage("loginForm", new FacesMessage(e.getMessage()));
            return null;
        }
    }

    public String logout() throws ServletException {
        HttpSession session = request.getSession();
        if(session != null) {
            request.logout();
            request.getSession().invalidate();
        }
        return "Index";
    }
}
