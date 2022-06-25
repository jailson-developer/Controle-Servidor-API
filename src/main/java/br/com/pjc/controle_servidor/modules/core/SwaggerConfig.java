package br.com.pjc.controle_servidor.modules.core;

import br.com.pjc.controle_servidor.modules.error.ErrorResponseBody;
import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        components = @Components(responses = {
                @APIResponse(responseCode = "500", ref = "#/components/schemas/ErrorResponseBody", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseBody.class)))
        }),

        info = @Info(
                title = "Controle de Servidor Público API",
                version = "1.0.1",
                contact = @Contact(
                        name = "Controle de Servidor Público API",
                        url = "http://localhost:8080/q/swagger-ui/#/",
                        email = "jailsonsalesribeiro@gmail.com"),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class SwaggerConfig extends Application {
}
