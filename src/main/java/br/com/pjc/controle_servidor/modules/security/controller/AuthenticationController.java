package br.com.pjc.controle_servidor.modules.security.controller;

import br.com.pjc.controle_servidor.modules.error.ClientPreconditionFailed;
import br.com.pjc.controle_servidor.modules.security.dto.AuthRequestDto;
import br.com.pjc.controle_servidor.modules.security.dto.AuthResponseDto;
import br.com.pjc.controle_servidor.modules.security.service.AuthenticationService;
import io.quarkus.security.Authenticated;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import lombok.SneakyThrows;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/autenticacao")
@Tag(name = "Autenticação", description = "Endpoint(s) relacionado(s) a Autenticação")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationController {
    @Inject
    AuthenticationService service;

    @SneakyThrows
    @PermitAll
    @Path("/login")
    @POST
    public AuthResponseDto login(@RequestBody AuthRequestDto request) {
        return service.login(request.getUsername(), request.getPassword());
    }


    @Path("/refresh")
    @PUT
    @Authenticated
    @Operation(description = "Passar o Token de refresh no Header. Ex: Authorization: Bearer eyJ0eXAiOiJK...")
    public AuthResponseDto refresh(@Context SecurityContext securityContext) {
        var user = securityContext.getUserPrincipal();
        if (user != null) {
            DefaultJWTCallerPrincipal jwtCallerPrincipal = (DefaultJWTCallerPrincipal) user;
            if (!"Refresh".equals(jwtCallerPrincipal.getClaim("type")))
                throw new ClientPreconditionFailed("Token de refresh inválido");
            return service.refreshToken(Long.valueOf(jwtCallerPrincipal.getClaim("userId").toString()));
        }
        return null;
    }
}
