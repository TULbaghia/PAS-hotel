package pl.lodz.p.pas.service.filters.reservationFilters.addReservationFilter;

import org.json.JSONObject;
import pl.lodz.p.pas.service.filters.genericFilterUtil.RequiredFieldsUtil;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
@PostAddReservationCheckBinding
public class PostAddReservationCheck implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        JSONObject jsonObject = RequiredFieldsUtil.getJsonObject(requestContext);

        String[] fields = new String[]{"guest", "apartment", "sellStartDate"};
        RequiredFieldsUtil.checkFieldsObject( jsonObject, fields.length, fields);
    }
}
