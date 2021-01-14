package pl.lodz.p.pas.service.filters.genericFilterUtil;

import org.json.JSONException;
import org.json.JSONObject;
import pl.lodz.p.pas.service.mapper.exception.ErrorProp;
import pl.lodz.p.pas.service.mapper.exception.RestException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class RequiredFieldsUtil {

    public static JSONObject getJsonObject(ContainerRequestContext requestContext) {
        String text;
        try (Scanner scanner = new Scanner(requestContext.getEntityStream(), StandardCharsets.UTF_8.name())) {
            text = scanner.useDelimiter("\\A").next();
        }
        requestContext.setEntityStream(new ByteArrayInputStream(text.getBytes()));
        return new JSONObject(text);
    }

    public static void checkFields(JSONObject jsonObject, int params, String... stringProperties) {
        List<ErrorProp> errorPropList = new ArrayList<>();
        if (jsonObject.length() != params) {
            errorPropList.add(new ErrorProp("Request", "Incorrect number of parameters"));
        }

        Arrays.stream(stringProperties).forEach(property -> {
            try {
                if (jsonObject.getString(property).isEmpty()) {
                    errorPropList.add(new ErrorProp(property, "is empty"));
                }
            } catch (JSONException e) {
                errorPropList.add(new ErrorProp("", e.getMessage()));
            }
        });

        if (errorPropList.size() > 0) {
            throw new RestException(Response.Status.BAD_REQUEST, errorPropList);
        }
    }
}
