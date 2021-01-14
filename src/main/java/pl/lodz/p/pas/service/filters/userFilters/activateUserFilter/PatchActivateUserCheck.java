package pl.lodz.p.pas.service.filters.userFilters.activateUserFilter;

import org.json.JSONException;
import org.json.JSONObject;
import pl.lodz.p.pas.service.filters.genericFilterUtil.RequiredFieldsUtil;
import pl.lodz.p.pas.service.mapper.exception.ErrorProp;
import pl.lodz.p.pas.service.mapper.exception.RestException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@PatchActivateUserCheckBinding
public class PatchActivateUserCheck implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        JSONObject jsonObject = RequiredFieldsUtil.getJsonObject(requestContext);

        RequiredFieldsUtil.checkFields(jsonObject, 2, "id");

        try {
            jsonObject.getBoolean("active");
        } catch (JSONException e) {
            throw new RestException(Response.Status.BAD_REQUEST, new ErrorProp("active", "is not boolean"));
        }
    }
}
