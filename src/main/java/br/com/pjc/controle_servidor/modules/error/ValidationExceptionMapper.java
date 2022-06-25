package br.com.pjc.controle_servidor.modules.error;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponseBody(exception.getMessage())).build();
    }
}
