package br.com.pjc.controle_servidor.modules.servidor.controller;

import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.security.utils.Roles;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorEfetivoEnderecoFuncionalDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorEfetivoLotacaoDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorEfetivoRequestDTO;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorEfetivoResponseDTO;
import br.com.pjc.controle_servidor.modules.servidor.filtro.ServidorFiltro;
import br.com.pjc.controle_servidor.modules.servidor.service.ServidorEfetivoServiceImpl;
import lombok.SneakyThrows;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.ValidationException;
import java.util.List;

@Path("/v1/servidores/efetivo")
@Tag(name = "Servidor Efetivo", description = "Endpoint(s) relacionado(s) a manipulação de Servidor Efetivo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServidorEfetivoController {
    @Inject
    ServidorEfetivoServiceImpl service;


    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar Servidor Efetivo", description = "Busca os dados do servidor de acordo com o ID")
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public ServidorEfetivoResponseDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }


    @POST
    @Operation(summary = "Salvar Servidor Efetivo", description = "Cria um novo Servidor Efetivo")
    @APIResponse(responseCode = "201", description = "Servidor salvo", content = @Content(schema = @Schema(implementation = ServidorEfetivoResponseDTO.class)))
    @RolesAllowed({Roles.ESCRITA})
    public Response salvar(@Valid @RequestBody ServidorEfetivoRequestDTO servidor) {
        return Response.created(null).entity(service.salvar(servidor)).build();
    }


    @PUT
    @Path("/{id}")
    @Operation(summary = "Editar Servidor Efetivo", description = "Edita os dados do Servidor Efetivo")
    @RolesAllowed({Roles.ESCRITA})
    public ServidorEfetivoResponseDTO editar(@Valid @RequestBody ServidorEfetivoRequestDTO servidor, @PathParam("id") Long id) {
        return service.editar(id, servidor);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir Servidor Efetivo", description = "Exclui os dados do Servidor Efetivo")
    @APIResponse(responseCode = "204", description = "Registro excluído com sucesso")
    @RolesAllowed({Roles.ESCRITA})
    public Response deleteById(@PathParam("id") Long id) {
        service.deleteById(id);
        return Response.noContent().build();
    }

    @SneakyThrows
    @GET
    @Path("/")
    @Operation(summary = "Buscar Servidores Efetivos", description = "Busca os servidores de acordo com o filtro informado")
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public PaginatedResponse<List<ServidorEfetivoResponseDTO>> findAll(@BeanParam PageRequest pageRequest, @BeanParam ServidorFiltro.ServidorEfetivoFiltro filtro) {
        if (filtro.ehVazio())
            throw new ValidationException("Informe um critério para a Pesquisa!");
        return service.findAll(pageRequest, filtro);
    }

    @GET
    @Path("/por-unidade/{unidadeId}")
    @Operation(summary = "Buscar Servidores Efetivos da Unidade", description = "Busca os servidores lotados na Unidade informada")
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public PaginatedResponse<List<ServidorEfetivoLotacaoDto>> servidoresPorUnidade(@PathParam("unidadeId") Long unidadeId, @BeanParam PageRequest pageRequest) {
        return service.servidoresPorUnidade(unidadeId, pageRequest);
    }
    @GET
    @Path("/endereco-funcional/{nomeServidor}")
    @Operation(summary = "Buscar Servidores Efetivos da Unidade", description = "Busca os servidores lotados na Unidade informada")
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public PaginatedResponse<List<ServidorEfetivoEnderecoFuncionalDto>> enderecoFuncional(@PathParam("nomeServidor") String nomeServidor, @BeanParam PageRequest pageRequest) {
        return service.enderecoFuncional(nomeServidor, pageRequest);
    }
}
