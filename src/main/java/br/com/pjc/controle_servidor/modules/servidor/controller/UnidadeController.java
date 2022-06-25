package br.com.pjc.controle_servidor.modules.servidor.controller;

import br.com.pjc.controle_servidor.modules.core.Func;
import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.security.utils.Roles;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorEfetivoResponseDTO;
import br.com.pjc.controle_servidor.modules.servidor.dto.UnidadeRequestDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.UnidadeResponseDto;
import br.com.pjc.controle_servidor.modules.servidor.service.IUnidadeService;
import lombok.SneakyThrows;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.ValidationException;
import java.util.List;

@Path("/v1/unidades")
@Tag(name = "Unidades", description = "Endpoint(s) relacionado(s) a manipulação de Unidades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UnidadeController {
    @Inject
    IUnidadeService service;


    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar Unidade", description = "Busca os dados do servidor de acordo com o ID")
    @RolesAllowed({Roles.LEITURA})
    public UnidadeResponseDto findById(@PathParam("id") Long id) {
        return service.findById(id);
    }


    @POST
    @Operation(summary = "Salvar Unidade", description = "Cria uma nova Unidade")
    @APIResponse(responseCode = "201", description = "Servidor salvo", content = @Content(schema = @Schema(implementation = ServidorEfetivoResponseDTO.class)))
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public Response salvar(@Valid @RequestBody UnidadeRequestDto unidade) {
        return Response.created(null).entity(service.salvar(unidade)).build();
    }


    @PUT
    @Path("/{id}")
    @Operation(summary = "Editar Unidade", description = "Edita os dados da Unidade")
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public UnidadeResponseDto editar(@Valid @RequestBody UnidadeRequestDto unidade, @PathParam("id") Long id) {
        return service.editar(id, unidade);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir Unidade", description = "Exclui os dados da Unidade")
    @APIResponse(responseCode = "204", description = "Registro excluído com sucesso")
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public Response deleteById(@PathParam("id") Long id) {
        service.deleteById(id);
        return Response.noContent().build();
    }

    @SneakyThrows
    @GET
    @Path("/")
    @Operation(summary = "Buscar de Unidades", description = "Busca as Unidades de acordo com o filtro informado")
    @RolesAllowed({Roles.LEITURA})
    public PaginatedResponse<List<UnidadeResponseDto>> findAll(@BeanParam PageRequest pageRequest, @QueryParam("nome") String nome, @QueryParam("sigla") String sigla) {
        if (Func.isNullOrEmpty(nome) && Func.isNullOrEmpty(sigla))
            throw new ValidationException("Informe um critério para a Pesquisa!");
        return service.findAll(pageRequest, nome, sigla);
    }
}
