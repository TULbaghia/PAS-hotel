package pl.lodz.p.pas.service.mapper.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestExceptionMapper implements ExceptionMapper<RestException> {
    @Override
    public Response toResponse(RestException exception) {
        return Response
                .status(exception.getResponseCode())
                .entity(new ErrorProps(exception.getResponseCode(), exception.getErrorProps()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
