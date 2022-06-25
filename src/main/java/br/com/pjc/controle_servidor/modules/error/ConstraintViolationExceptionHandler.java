package br.com.pjc.controle_servidor.modules.error;

import lombok.Getter;
import lombok.Setter;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Inject
    Logger log;

    @Override
    public Response toResponse(ConstraintViolationException e) {
        log.errorf("Mensagem: %s", e.getMessage());
        List<ConstraintError> constraintErrors = new ArrayList<>(e.getConstraintViolations().size());
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            constraintErrors.add(new ConstraintError(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(constraintErrors).build();
    }

    @Getter
    @Setter
    private static class ConstraintError implements Serializable {
        private String field;
        private String message;

        public ConstraintError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}