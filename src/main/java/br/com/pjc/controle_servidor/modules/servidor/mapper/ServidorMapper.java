package br.com.pjc.controle_servidor.modules.servidor.mapper;

import br.com.pjc.controle_servidor.modules.cidade.model.Cidade;
import br.com.pjc.controle_servidor.modules.pessoa.mapper.EnderecoMapper;
import br.com.pjc.controle_servidor.modules.pessoa.mapper.PessoaMapper;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorEfetivoRequestDTO;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorEfetivoResponseDTO;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorTemporarioRequestDTO;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorTemporarioResponseDTO;
import br.com.pjc.controle_servidor.modules.servidor.model.ServidorEfetivo;
import br.com.pjc.controle_servidor.modules.servidor.model.ServidorTemporario;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PessoaMapper.class, EnderecoMapper.class})
public interface ServidorMapper {
    ServidorMapper INSTANCE = Mappers.getMapper(ServidorMapper.class);
    @Mapping(target = "enderecos", source = "endereco")
    ServidorEfetivo servidorEfetivoDtoToServidorEfetivo(ServidorEfetivoRequestDTO servidorEfetivoDto, @Context Cidade cidade);
    @Mapping(source = "enderecos", target = "endereco")
    ServidorEfetivoResponseDTO servidorEfetivoToServidorEfetivoDto(ServidorEfetivo servidorEfetivo);

    @Mapping(target = "enderecos", ignore = true)
    void updateServidorEfetivoFromServidorEfetivoDto(ServidorEfetivoRequestDTO servidorEfetivoDto, @MappingTarget ServidorEfetivo servidorEfetivo);
    @Mapping(target = "enderecos", source = "endereco")
    ServidorTemporario servidorTemporarioDtoToServidor(ServidorTemporarioRequestDTO dto, @Context Cidade cidade);
    @Mapping(target = "enderecos", ignore = true)
    void servidorTemporarioDtoToServidor(@MappingTarget ServidorTemporario servidor, ServidorTemporarioRequestDTO dto);
    @Mapping(source = "enderecos", target = "endereco")
    ServidorTemporarioResponseDTO servidorToServidorTemporarioDto(ServidorTemporario dto);
}
