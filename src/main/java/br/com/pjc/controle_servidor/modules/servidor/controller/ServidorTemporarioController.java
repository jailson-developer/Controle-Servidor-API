package br.com.pjc.controle_servidor.modules.servidor.controller;

import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.pessoa.dto.PessoaResponseDTO;
import br.com.pjc.controle_servidor.modules.security.utils.Roles;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorTemporarioRequestDTO;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorTemporarioResponseDTO;
import br.com.pjc.controle_servidor.modules.servidor.filtro.ServidorFiltro;
import br.com.pjc.controle_servidor.modules.servidor.service.ServidorTemporarioServiceImpl;
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
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Path("/v1/servidores/temporario")
@Tag(name = "Servidor Temporário", description = "Endpoint(s) relacionado(s) a manipulação de Servidor Temporário")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServidorTemporarioController {
    @Inject
    ServidorTemporarioServiceImpl service;


    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar Servidor Temporário", description = "Busca os dados do servidor de acordo com o ID")
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public ServidorTemporarioResponseDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }


    @POST
    @Operation(summary = "Salvar Servidor Temporário", description = "Cria um novo servidor temporário")
    @APIResponse(responseCode = "201", description = "Pessoa salva", content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class)))
    @RolesAllowed({Roles.ESCRITA})
    public Response salvar(@Valid @RequestBody ServidorTemporarioRequestDTO servidor) {
        return Response.created(null).entity(service.salvar(servidor)).build();
    }


    @PUT
    @Path("/{id}")
    @Operation(summary = "Editar Servidor Temporário", description = "Edita os dados do Servidor Temporário")
    @RolesAllowed({Roles.ESCRITA})
    public ServidorTemporarioResponseDTO editar(@Valid @RequestBody ServidorTemporarioRequestDTO servidor, @PathParam("id") Long id) {
        return service.editar(id, servidor);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir Servidor Temporário", description = "Exclui os dados do Servidor Temporário")
    @APIResponse(responseCode = "204", description = "Registro excluído com sucesso")
    @RolesAllowed({Roles.ESCRITA})
    public Response deleteById(@PathParam("id") Long id) {
        service.deleteById(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/")
    @Operation(summary = "Buscar Servidores Temporários", description = "Busca os servidores de acordo com o filtro informado")
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public PaginatedResponse<List<ServidorTemporarioResponseDTO>> findAll(@BeanParam PageRequest pageRequest, @BeanParam ServidorFiltro.ServidorTemporarioFiltro filtro) {
        if (filtro.ehVazio())
            throw new ValidationException("Informe um critério para a Pesquisa!");
        return service.findAll(pageRequest, filtro);
    }
}
