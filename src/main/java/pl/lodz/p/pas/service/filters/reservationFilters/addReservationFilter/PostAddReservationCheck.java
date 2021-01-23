package pl.lodz.p.pas.service.filters.reservationFilters.addReservationFilter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONException;
import org.json.JSONObject;
import pl.lodz.p.pas.service.filters.genericFilterUtil.RequiredFieldsUtil;
import pl.lodz.p.pas.service.mapper.exception.ErrorProp;
import pl.lodz.p.pas.service.mapper.exception.RestException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;

@Provider
@PostAddReservationCheckBinding
public class PostAddReservationCheck implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        JSONObject jsonObject = RequiredFieldsUtil.getJsonObject(requestContext);

        List<ErrorProp> errorPropList = new ArrayList<>();

        if (jsonObject.length() != 3 && jsonObject.length() != 2) {
            errorPropList.add(new ErrorProp("Request", "Incorrect number of parameters"));
        }


        try {
            if (!jsonObject.getJSONObject("guest").has("id")) {
                errorPropList.add(new ErrorProp("guest", "id is not defined"));
            }
        } catch (JSONException e) {
            errorPropList.add(new ErrorProp("", e.getMessage()));
        }

        try {
            if (!jsonObject.getJSONObject("apartment").has("id")) {
                errorPropList.add(new ErrorProp("apartment", "id is not defined"));
            }
        } catch (JSONException e) {
            errorPropList.add(new ErrorProp("", e.getMessage()));
        }

        if (jsonObject.length() == 3) {
            try {
                if (jsonObject.getString("reservationStartDate").isEmpty()) {
                    errorPropList.add(new ErrorProp("reservationStartDate", "is not defined"));
                }
                if(DateTime.parse(jsonObject.getString("reservationStartDate"), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")) == null) {
                    errorPropList.add(new ErrorProp("reservationStartDate", "is not datetime"));
                }
            } catch (JSONException e) {
                errorPropList.add(new ErrorProp("", e.getMessage()));
            }
        }

        if (errorPropList.size() > 0) {
            throw new RestException(Response.Status.BAD_REQUEST, errorPropList);
        }

    }
}
