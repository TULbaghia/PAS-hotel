package pl.lodz.p.pas.service.mapper.exception;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.nio.file.AccessDeniedException;

@Provider
public class AccessDeniedExceptionMapper implements ExceptionMapper<ForbiddenException> {
    @Override
    public Response toResponse(ForbiddenException exception) {
        return Response.status(403).entity(new ErrorProps("403", exception.getMessage())).type(MediaType.APPLICATION_JSON).build();
    }
}
