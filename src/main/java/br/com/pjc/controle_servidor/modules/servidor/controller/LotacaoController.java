package br.com.pjc.controle_servidor.modules.servidor.controller;

import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.security.utils.Roles;
import br.com.pjc.controle_servidor.modules.servidor.dto.LotacaoRequestDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.LotacaoResponseDto;
import br.com.pjc.controle_servidor.modules.servidor.filtro.ServidorFiltro;
import br.com.pjc.controle_servidor.modules.servidor.service.ILotacaoService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1/lotacoes")
@Tag(name = "Lotacoes", description = "Endpoint(s) relacionado(s) a manipulação de Lotacões")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LotacaoController {
    @Inject
    ILotacaoService service;


    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar Lotacao", description = "Busca os dados da Lotação de acordo com o ID")
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public LotacaoResponseDto findById(@PathParam("id") Long id) {
        return service.findById(id);
    }


    @POST
    @Operation(summary = "Salvar Lotação", description = "Cria uma nova Lotação")
    @APIResponse(responseCode = "201", description = "Lotação salvo", content = @Content(schema = @Schema(implementation = LotacaoResponseDto.class)))
    @RolesAllowed(Roles.ESCRITA)
    public Response salvar(@Valid @RequestBody LotacaoRequestDto lotacao) {
        return Response.created(null).entity(service.salvar(lotacao)).build();
    }


    @PUT
    @Path("/{id}")
    @Operation(summary = "Editar Lotação", description = "Edita os dados da Lotação")
    @RolesAllowed(Roles.ESCRITA)
    public LotacaoResponseDto editar(@Valid @RequestBody LotacaoRequestDto lotacao, @PathParam("id") Long id) {
        return service.editar(id, lotacao);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir Lotação", description = "Exclui os dados da Lotação")
    @APIResponse(responseCode = "204", description = "Registro excluído com sucesso")
    @RolesAllowed(Roles.ESCRITA)
    public Response deleteById(@PathParam("id") Long id) {
        service.deleteById(id);
        return Response.noContent().build();
    }


    @GET
    @Path("/")
    @Operation(summary = "Busca de Lotações", description = "Busca as Lotações de acordo com o filtro informado")
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public PaginatedResponse<List<LotacaoResponseDto>> findAll(@BeanParam PageRequest pageRequest, @BeanParam ServidorFiltro.LotacaoFiltro lotacaoFiltro) {
        if (lotacaoFiltro.ehVazio())
            throw new ValidationException("Informe um critério para a Pesquisa!");
        return service.findAll(pageRequest, lotacaoFiltro);
    }
}
