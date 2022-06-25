package br.com.pjc.controle_servidor.modules.error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ClientPreconditionFailedMapper implements ExceptionMapper<ClientPreconditionFailed> {
    @Override
    public Response toResponse(ClientPreconditionFailed e) {
        return Response.status(Response.Status.PRECONDITION_FAILED).entity(ErrorResponseBody.builder().message(e.getMessage()).build()).build();
    }
}
