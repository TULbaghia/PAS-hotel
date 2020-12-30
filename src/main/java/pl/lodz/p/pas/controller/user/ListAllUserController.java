package pl.lodz.p.pas.controller.user;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.user.User;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ViewScoped
@Named
public class ListAllUserController implements Serializable {

    @Inject
    private UserManager userManager;

    @Getter
    private Map<UUID, Boolean> currentUserStatuses = new HashMap<>();

    @Getter
    private List<User> currentUsers = new ArrayList<>();

    @Getter
    @Setter
    private int currentPage = 1;

    @Getter
    @Setter
    private int itemsPerPage = 5;

    @Getter
    @Setter
    private List<Integer> availablePages;

    @Getter @Setter
    private String searchData = "";

    public List<User> getAllUsers() {
        return currentUsers;
    }

    @PostConstruct
    public void initCurrentStatuses() {
        userManager.getAll().forEach(x -> currentUserStatuses.put(x.getId(), x.isActive()));
        availablePages = IntStream.range(1, 1 + (int) Math.ceil(userManager.filter(x -> x.toString().contains(searchData)).size() / (double) itemsPerPage))
                .boxed().collect(Collectors.toList());
        currentPage = Math.min(currentPage, availablePages.get(availablePages.size() - 1));
        currentUsers = userManager.paginate(itemsPerPage, currentPage, x -> x.toString().contains(searchData));
    }

}
