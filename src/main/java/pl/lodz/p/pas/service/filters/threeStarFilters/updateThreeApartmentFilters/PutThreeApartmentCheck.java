package pl.lodz.p.pas.service.filters.threeStarFilters.updateThreeApartmentFilters;

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
@PutThreeApartmentCheckBinding
public class PutThreeApartmentCheck implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        JSONObject jsonObject = RequiredFieldsUtil.getJsonObject(requestContext);

        List<ErrorProp> errorPropList = new ArrayList<>();

        if (jsonObject.length() != 5) {
            errorPropList.add(new ErrorProp("Request", "Incorrect number of parameters"));
        }

        try {
            if (jsonObject.getString("id").isEmpty()) {
                errorPropList.add(new ErrorProp("id", "is not defined"));
            }
        } catch (JSONException e) {
            errorPropList.add(new ErrorProp("", e.getMessage()));
        }

        try {
            jsonObject.getInt("howManyBeds");
        } catch (JSONException e) {
            errorPropList.add(new ErrorProp("", e.getMessage()));
        }

        try {
            jsonObject.getInt("doorNumber");
        } catch (JSONException e) {
            errorPropList.add(new ErrorProp("", e.getMessage()));
        }

        try {
            jsonObject.getDouble("basePricePerDay");
        } catch (JSONException e) {
            errorPropList.add(new ErrorProp("", e.getMessage()));
        }

        try {
            if (jsonObject.getString("bonus").isEmpty()) {
                errorPropList.add(new ErrorProp("bonus", "is not defined"));
            }
        } catch (JSONException e) {
            errorPropList.add(new ErrorProp("", e.getMessage()));
        }

        if (errorPropList.size() > 0) {
            throw new RestException(Response.Status.BAD_REQUEST, errorPropList);
        }

    }
}
