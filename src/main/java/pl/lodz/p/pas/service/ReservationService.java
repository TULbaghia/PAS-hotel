package pl.lodz.p.pas.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import org.joda.time.DateTime;
import org.json.JSONObject;
import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.manager.ReservationManager;
import pl.lodz.p.pas.manager.UserManager;
import pl.lodz.p.pas.manager.exception.ManagerException;
import pl.lodz.p.pas.model.exception.GuestException;
import pl.lodz.p.pas.model.exception.ReservationException;
import pl.lodz.p.pas.model.resource.Apartment;
import pl.lodz.p.pas.model.resource.Reservation;
import pl.lodz.p.pas.model.user.Guest;
import pl.lodz.p.pas.model.user.Manager;
import pl.lodz.p.pas.model.user.User;
import pl.lodz.p.pas.repository.exception.RepositoryException;
import pl.lodz.p.pas.service.dto.Mapper;
import pl.lodz.p.pas.service.dto.ReservationDto;
import pl.lodz.p.pas.service.filters.reservationFilters.addReservationFilter.PostAddReservationCheckBinding;
import pl.lodz.p.pas.service.filters.reservationFilters.endReservationFilter.PatchEndReservationCheckBinding;
import pl.lodz.p.pas.service.filters.reservationFilters.updateReservationFilter.PutUpdateReservationCheckBinding;
import pl.lodz.p.pas.service.mapper.exception.ErrorProp;
import pl.lodz.p.pas.service.mapper.exception.RestException;
import pl.lodz.p.pas.service.views.Views;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.UUID;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("reservation")
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
    public String addReservation(ReservationDto reservationDto, @Context SecurityContext securityContext) throws JsonProcessingException {
        String currentUser = securityContext.getUserPrincipal().getName();

        Apartment apartment = apartmentManager.get(reservationDto.getApartment().getId());
        Guest guest = (Guest) userManager.get(reservationDto.getGuest().getId());
        DateTime dateTime = reservationDto.getReservationStartDate();
        guestApartmentNullPtr(guest, apartment);

        User user = userManager.get(currentUser);
        if (user instanceof Guest && !guest.getLogin().equals(currentUser)) {
            return null;
        }
        Reservation newReservation = Reservation.builder().guest(guest).apartment(apartment).reservationStartDate(dateTime).build();
        try {
            reservationManager.add(newReservation);
        } catch (ManagerException | RepositoryException e) {
            throw new RestException(Response.Status.BAD_REQUEST, new ErrorProp("addReservation", e.getMessage()));
        }
        return new Mapper().writeAsString(Views.Public.class, reservationManager.get(newReservation.getId()));
    }

    @PUT
    @PutUpdateReservationCheckBinding
    public String updateReservation(ReservationDto reservationDto) {
        Apartment apartment = apartmentManager.get(reservationDto.getApartment().getId());
        Guest guest = (Guest) userManager.get(reservationDto.getGuest().getId());
        guestApartmentNullPtr(guest, apartment);

        Reservation newReservation = Reservation.builder()
                .id(reservationDto.getId())
                .guest(guest)
                .apartment(apartment)
                .reservationStartDate(reservationDto.getReservationStartDate())
                .build();

        try {
            reservationManager.update(newReservation);
        } catch (ManagerException | RepositoryException e) {
            throw new RestException(Response.Status.BAD_REQUEST, new ErrorProp("updateReservation", e.getMessage()));
        }
        return new Mapper().writeAsString(Views.Public.class, reservationManager.get(newReservation.getId()));
    }

    @Path("/end")
    @PATCH
    @PatchEndReservationCheckBinding
    public String endReservation(ReservationDto reservationDto) throws ReservationException, GuestException {
        Reservation reservation = reservationManager.get(reservationDto.getId());
        reservation.endReservation();
        return new Mapper().writeAsString(Views.Public.class, reservationManager.get(reservation.getId()));
    }

    @Path("/{uuid}")
    @DELETE
    public String deleteReservation(@PathParam("uuid") String reservationId) {
        try {
            Reservation reservation = reservationManager.get(UUID.fromString(reservationId));
            reservationManager.delete(reservation.getId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", true);
            return jsonObject.toString();
        } catch (Exception e) {
            throw new RestException(Response.Status.BAD_REQUEST, new ErrorProp("deleteReservation", e.getMessage()));
        }
    }

    private void guestApartmentNullPtr(Guest guest, Apartment apartment) {
        if(guest == null) {
            throw new RestException(Response.Status.BAD_REQUEST, new ErrorProp("guest", "does not exist"));
        }
        if(apartment == null) {
            throw new RestException(Response.Status.BAD_REQUEST, new ErrorProp("apartment", "does not exist"));
        }
    }
}
