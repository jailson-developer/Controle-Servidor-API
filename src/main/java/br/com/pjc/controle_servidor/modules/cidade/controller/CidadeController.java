package br.com.pjc.controle_servidor.modules.cidade.controller;

import br.com.pjc.controle_servidor.modules.cidade.dto.CidadeDto;
import br.com.pjc.controle_servidor.modules.cidade.service.CidadeService;
import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/v1/cidades")
@Tag(name = "Cidades", description = "Endpoint(s) relacionado(s) a manipulação de Cidade(s)")
@RolesAllowed({"roleEscrita", "roleLeitura"})
public class CidadeController {
    @Inject
    CidadeService service;

    @GET
    @Operation(description = "Busca as cidades de acordo com os critérios informados", summary = "Busca de Cidades")
    @Path("/{nome}")
    public PaginatedResponse<List<CidadeDto>> buscarCidades(@PathParam("nome") String nome, @BeanParam PageRequest pageRequest) {
        return service.buscarCidades(nome, pageRequest);
    }
}
