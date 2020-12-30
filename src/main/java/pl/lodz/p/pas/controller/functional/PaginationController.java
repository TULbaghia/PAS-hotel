package pl.lodz.p.pas.controller.functional;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
@Getter
@Setter
public class PaginationController implements Serializable {
    private int reservationItemsPerPage = 5;
    private int reservationCurrentPage = 1;

    private int apartmentItemsPerPage = 5;
    private int apartmentCurrentPage = 1;

    private int userItemsPerPage = 5;
    private int userCurrentPage = 1;
}
