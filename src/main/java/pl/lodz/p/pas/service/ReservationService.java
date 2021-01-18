package pl.lodz.p.pas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.commons.beanutils.BeanUtils;
import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.manager.ReservationManager;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.model.exception.GuestException;
import pl.lodz.p.pas.model.exception.ReservationException;
import pl.lodz.p.pas.model.resource.Reservation;
import pl.lodz.p.pas.model.user.Admin;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.model.user.Manager;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.service.dto.IdTraitDto;
import pl.lodz.p.pas.service.dto.Mapper;
import pl.lodz.p.pas.service.dto.ReservationDto;
import pl.lodz.p.pas.service.filters.reservationFilters.addReservationFilter.PostAddReservationCheckBinding;
import pl.lodz.p.pas.service.filters.reservationFilters.endReservationFilter.PatchEndReservationCheckBinding;
import pl.lodz.p.pas.service.filters.reservationFilters.updateReservationFilter.PutUpdateReservationCheckBinding;
import pl.lodz.p.pas.service.filters.userFilters.activateUserFilter.PatchActivateUserCheckBinding;
import pl.lodz.p.pas.service.filters.userFilters.addUserFilter.PostAddUserCheckBinding;
import pl.lodz.p.pas.service.filters.userFilters.updateUserFilter.PutUpdateUserCheckBinding;
import pl.lodz.p.pas.service.mapper.exception.ErrorProp;
import pl.lodz.p.pas.service.mapper.exception.RestException;
import pl.lodz.p.pas.service.views.Views;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("1/reservation")
public class ReservationService {

    @Inject @Getter
    private ReservationManager reservationManager;

    @Inject @Getter
    private UserManager userManager;

    @Inject @Getter
    private ApartmentManager apartmentManager;

    @GET
    public String getAllReservations(@Context SecurityContext securityContext) {
        String currentUser = securityContext.getUserPrincipal().getName();
        User user = userManager.get(currentUser);
        if (user instanceof Manager) {
            return new Mapper().writeAsString(Views.Public.class, reservationManager.getAll());
        } else if (user instanceof Guest){
            return new Mapper().writeAsString(Views.Public.class, reservationManager.getGuestReservations((Guest) user));
        }
        return null;
    }

    @Path("/{uuid}")
    @GET
    public String getReservation(@PathParam("uuid") String id,  @Context SecurityContext securityContext) {
        String currentUser = securityContext.getUserPrincipal().getName();
        User user = userManager.get(currentUser);
        Reservation reservation = reservationManager.get(UUID.fromString(id));
        if (user instanceof Manager) {
            return new Mapper().writeAsString(Views.Public.class, reservation);
        } else if (user instanceof Guest && reservation.getGuest().getLogin().equals(currentUser)){
            return new Mapper().writeAsString(Views.Public.class, reservation);
        }
        return null;
    }

    @POST
    @PostAddReservationCheckBinding
    public String addReservation(@Valid Reservation reservation, @Context SecurityContext securityContext) {
        String currentUser = securityContext.getUserPrincipal().getName();
        User user = userManager.get(currentUser);
        Reservation res = reservationManager.get(reservation.getId());
        if (user instanceof Guest && !(res.getGuest().getLogin().equals(currentUser))) {
            return null;
        }
        reservationManager.add(reservation);
        return new Mapper().writeAsString(Views.Public.class, reservation);
    }

    @PUT
    @PutUpdateReservationCheckBinding
    public String updateReservation(String reservationDto) throws JsonProcessingException {
        ReservationDto reservation = new ObjectMapper().readValue(reservationDto, ReservationDto.class);
        Reservation res = null;
        try {
            res = (Reservation) BeanUtils.cloneBean(reservationManager.get(reservation.getId().getId()));
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RestException(Response.Status.PRECONDITION_FAILED, new ErrorProp("updateReservation", "bean clone error"));
        }
        assert res != null;
        res.setGuest((Guest) userManager.get(reservation.getGuest().getId()));
        res.setApartment(apartmentManager.get(reservation.getApartment().getId()));
        reservationManager.update(res);
        return new Mapper().writeAsString(Views.Public.class, reservationManager.get(reservation.getId().getId()));
    }

    @Path("/end")
    @PATCH
    @PatchEndReservationCheckBinding
    public String endReservation(Reservation reservation) throws ReservationException, GuestException {
        Reservation res = null;
        try {
            res = (Reservation) BeanUtils.cloneBean(reservationManager.get(reservation.getId()));
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RestException(Response.Status.PRECONDITION_FAILED, new ErrorProp("updateReservation", "bean clone error"));
        }
        assert res != null;
        res.endReservation();
        reservationManager.update(res);
        return new Mapper().writeAsString(Views.Public.class, reservationManager.get(reservation.getId()));
    }
}
