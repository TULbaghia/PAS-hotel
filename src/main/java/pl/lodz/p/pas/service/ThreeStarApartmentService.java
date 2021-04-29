package pl.lodz.p.pas.service;

import lombok.Getter;
import org.json.JSONObject;
import pl.lodz.p.pas.manager.ApartmentManager;
import pl.lodz.p.pas.manager.exception.ManagerException;
import pl.lodz.p.pas.model.resource.ThreeStarApartment;
import pl.lodz.p.pas.repository.exception.RepositoryException;
import pl.lodz.p.pas.service.dto.Mapper;
import pl.lodz.p.pas.service.filters.threeStarFilters.addThreeApartmentFilters.PostThreeApartmentCheckBinding;
import pl.lodz.p.pas.service.filters.threeStarFilters.updateThreeApartmentFilters.PutThreeApartmentCheckBinding;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@Path("threestar")
public class ThreeStarApartmentService {

    @Inject @Getter
    private ApartmentManager apartmentManager;

    @GET
    public String getAllThreeStar() {
        return new Mapper().writeAsString(Views.Public.class,
                apartmentManager
                        .filter(x -> x instanceof ThreeStarApartment).stream()
                        .map(x -> (ThreeStarApartment) x)
                        .collect(Collectors.toList()));
    }

    @Path("/{uuid}")
    @GET
    public String getThreeStar(@PathParam("uuid") String id, @Context SecurityContext securityContext)  {
        ThreeStarApartment apartment;
        try {
            apartment = (ThreeStarApartment) apartmentManager.get(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            apartment = (ThreeStarApartment) apartmentManager.get(Integer.parseInt(id));
        }
        return new Mapper().writeAsString(Views.Public.class, apartment);
    }

    @POST
    @PostThreeApartmentCheckBinding
    public String addApartment(@Valid ThreeStarApartment apartment) {
        try {
            apartmentManager.add(apartment);
        } catch (ManagerException | RepositoryException e) {
            throw new RestException(Response.Status.NOT_MODIFIED, new ErrorProp("addApartment", e.getMessage()));
        }
        return new Mapper().writeAsString(Views.Public.class, apartmentManager.get(apartment.getId()));
    }

    @PUT
    @PutThreeApartmentCheckBinding
    public String updateApartment(@Valid ThreeStarApartment apartment) {

        ThreeStarApartment newApartment = ThreeStarApartment.builder()
                .id(apartment.getId())
                .howManyBeds(apartment.getHowManyBeds())
                .doorNumber(apartment.getDoorNumber())
                .basePricePerDay(apartment.getBasePricePerDay())
                .bonus(apartment.getBonus())
                .build();

        try {
            apartmentManager.update(newApartment);
        } catch (ManagerException | RepositoryException e) {
            throw new RestException(Response.Status.NOT_MODIFIED, new ErrorProp("updateApartment", e.getMessage()));
        }
        return new Mapper().writeAsString(Views.Public.class, apartmentManager.get(apartment.getId()));
    }

    @Path("/{uuid}")
    @DELETE
    public String deleteApartment(@PathParam("uuid") String apartmentId) {
        try {
            ThreeStarApartment threeStarApartment = (ThreeStarApartment) apartmentManager.get(UUID.fromString(apartmentId));
            apartmentManager.delete(threeStarApartment.getId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", true);
            return jsonObject.toString();
        } catch (Exception e) {
            throw new RestException(Response.Status.BAD_REQUEST, new ErrorProp("deleteApartment", e.getMessage()));
        }
    }
}
