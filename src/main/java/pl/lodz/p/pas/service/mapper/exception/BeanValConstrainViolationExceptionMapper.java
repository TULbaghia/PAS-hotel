package pl.lodz.p.pas.service.mapper.exception;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BeanValConstrainViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        System.out.println("BeanValConstrainViolationExceptionMapper in action");

        ConstraintViolation<Object> cv = (ConstraintViolation<Object>) e.getConstraintViolations().toArray()[0];

        var propPath = StringUtils.substringAfterLast(cv.getPropertyPath().toString(), ".");

        return Response.status(Response.Status.PAYMENT_REQUIRED)
                .entity(new ErrorProps("400", propPath + " " + cv.getMessage()))
                .build();
    }

}