package pl.lodz.p.pas.service.mapper.exception;

import lombok.Getter;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RestException extends RuntimeException {
    @Getter
    public Response.Status responseCode;

    @Getter
    public Collection<ErrorProp> errorProps;

    public RestException(Response.Status responseCode, Collection<ErrorProp> collection) {
        this.responseCode = responseCode;
        this.errorProps = collection;
    }

    public RestException(Response.Status responseCode, ErrorProp errorProp) {
        this.responseCode = responseCode;
        List<ErrorProp> errorPropList = new ArrayList<>();
        errorPropList.add(errorProp);
        this.errorProps = errorPropList;
    }
}
