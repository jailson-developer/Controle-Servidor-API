package br.com.pjc.controle_servidor.modules.pessoa.controller;

import br.com.pjc.controle_servidor.modules.core.IdDto;
import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.pessoa.dto.*;
import br.com.pjc.controle_servidor.modules.pessoa.service.IFotoService;
import br.com.pjc.controle_servidor.modules.pessoa.service.PessoaService;
import br.com.pjc.controle_servidor.modules.security.utils.Roles;
import lombok.SneakyThrows;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.ValidationException;
import java.util.List;

@Path("/v1/pessoas")
@Tag(name = "Pessoas", description = "Endpoint(s) relacionado(s) a manipulação de Pessoas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaController {
    @Inject
    PessoaService service;

    @Inject
    IFotoService fotoService;

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar Pessoa", description = "Busca os dados da pessoa de acordo com o ID")
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public PessoaResponseDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @SneakyThrows
    @GET
    @Path("/")
    @Operation(summary = "Buscar Pessoas", description = "Busca as pessoas de acordo com o filtro informado")
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public PaginatedResponse<List<PessoaResponseDTO>> findAll(@BeanParam PageRequest pageRequest, @BeanParam FiltroPessoaDTO filtro) {
        if (filtro.ehVazio())
            throw new ValidationException("Informe um critério para a Pesquisa!");
        return service.findAll(pageRequest, filtro);
    }


    @POST
    @Operation(summary = "Salvar Pessoa", description = "Cria uma nova Pessoa")
    @APIResponse(responseCode = "201", description = "Pessoa salva", content = @Content(schema = @Schema(implementation = PessoaResponseDTO.class)))
    @RolesAllowed({Roles.ESCRITA})
    public Response salvar(@Valid @RequestBody PessoaRequestDTO pessoa) {
        return Response.created(null).entity(service.salvar(pessoa)).build();
    }

    @POST
    @Path("/{pessoaId}/fotos")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA, schema = @Schema(implementation = FotoRequestDto.class)))
    @APIResponse(content = {@Content(schema = @Schema(implementation = IdDto.class))}, responseCode = "201")
    @RolesAllowed({Roles.ESCRITA})
    public Response uploadFoto(@PathParam("pessoaId") Long pessoaId, @Parameter(hidden = true) MultipartFormDataInput input) {
        return Response.created(null).entity(fotoService.uploadFoto(pessoaId, new FotoRequestDto(input))).build();
    }

    @GET
    @Path("/{pessoaId}/fotos")
    @Operation(summary = "Buscar Fotos", description = "Busca as fotos da Pessoa/Servidor")
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public List<FotoResponseDTO> buscarFotos(@PathParam("pessoaId") Long pessoaId) {
        return fotoService.buscarFotos(pessoaId);
    }

    @GET
    @Path("/fotos/{fotoId}")
    @Operation(summary = "Buscar Foto", description = "Busca a foto da Pessoa/Servidor de acordo com o Id")
    @RolesAllowed({Roles.ESCRITA, Roles.LEITURA})
    public FotoResponseDTO buscarFoto(@PathParam("fotoId") Long fotoId) {
        return fotoService.buscarFoto(fotoId);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Editar Pessoa", description = "Edita os dados da Pessoa")
    @RolesAllowed({Roles.ESCRITA})
    public PessoaResponseDTO editar(@Valid @RequestBody PessoaRequestDTO pessoa, @PathParam("id") Long id) {
        return service.editar(id, pessoa);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Excluir Pessoa", description = "Exclui os dados da Pessoa")
    @APIResponse(responseCode = "204", description = "Registro excluído com sucesso")
    @RolesAllowed({Roles.ESCRITA})
    public Response deleteById(@PathParam("id") Long id) {
        service.deleteById(id);
        return Response.noContent().build();
    }
}
