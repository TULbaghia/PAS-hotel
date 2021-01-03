package pl.lodz.p.pas.service.mapper.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RepositoryExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        return Response.status(400).entity(new ErrorProps("400", exception.getMessage())).type(MediaType.APPLICATION_JSON).build();
    }
}
