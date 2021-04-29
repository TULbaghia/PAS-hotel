package pl.lodz.p.pas.service.filters.reservationFilters.endReservationFilter;

import org.json.JSONObject;
import pl.lodz.p.pas.service.filters.genericFilterUtil.RequiredFieldsUtil;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
@PatchEndReservationCheckBinding
public class PatchEndReservationCheck implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        JSONObject jsonObject = RequiredFieldsUtil.getJsonObject(requestContext);

        RequiredFieldsUtil.checkFieldsString(jsonObject, 1, "id");
    }
}
