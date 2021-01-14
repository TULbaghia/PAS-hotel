package pl.lodz.p.pas.service.filters.userFilters.updateUserFilter;

import org.json.JSONObject;
import pl.lodz.p.pas.service.filters.genericFilterUtil.RequiredFieldsUtil;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
@PutUpdateUserCheckBinding
public class PutUpdateUserCheck implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        JSONObject jsonObject = RequiredFieldsUtil.getJsonObject(requestContext);

        String[] fields = new String[]{"id", "password", "firstname", "lastname"};
        RequiredFieldsUtil.checkFields( jsonObject, fields.length, fields);
    }
}
