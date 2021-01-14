package pl.lodz.p.pas.service.filters.userFilters.addUserFilter;

import org.json.JSONObject;
import pl.lodz.p.pas.service.filters.genericFilterUtil.RequiredFieldsUtil;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
@PostAddUserCheckBinding
public class PostAddUserCheck implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        JSONObject jsonObject = RequiredFieldsUtil.getJsonObject(requestContext);

        String[] fields = new String[]{"login", "password", "firstname", "lastname"};
        RequiredFieldsUtil.checkFields( jsonObject, fields.length, fields);
    }
}
