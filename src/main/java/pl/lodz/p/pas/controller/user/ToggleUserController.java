package pl.lodz.p.pas.controller.user;

import pl.lodz.p.pas.controller.user.admin.ListAllAdminController;
import pl.lodz.p.pas.controller.user.guest.ListAllGuestController;
import pl.lodz.p.pas.controller.user.manager.ListAllManagerController;
import pl.lodz.p.pas.model.user.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class ToggleUserController {

    @Inject
    private ListAllUserController listAllUserController;

    @Inject
    private ListAllGuestController listAllGuestController;

    @Inject
    private ListAllManagerController listAllManagerController;

    @Inject
    private ListAllAdminController listAllAdminController;

    // TODO: managers use?
    public String toggleUser(User user) {
        user.setActive(!listAllUserController.getCurrentUserStatuses().get(user.getId()));
        listAllUserController.initCurrentStatuses();
        return "ListAllUser";
    }

    public String toggleUserGuest(User user) {
        user.setActive(!listAllGuestController.getCurrentUserStatuses().get(user.getId()));
        listAllGuestController.initCurrentStatuses();
        return "ListAllGuest";
    }

    public String toggleUserManager(User user) {
        user.setActive(!listAllManagerController.getCurrentUserStatuses().get(user.getId()));
        listAllManagerController.initCurrentStatuses();
        return "ListAllManager";
    }

    public String toggleUserAdmin(User user) {
        user.setActive(!listAllAdminController.getCurrentUserStatuses().get(user.getId()));
        listAllAdminController.initCurrentStatuses();
        return "ListAllAdmin";
    }

}
