package br.com.pjc.controle_servidor.modules.pessoa.mapper;

import br.com.pjc.controle_servidor.modules.cidade.model.Cidade;
import br.com.pjc.controle_servidor.modules.pessoa.dto.EnderecoDto;
import br.com.pjc.controle_servidor.modules.pessoa.model.Endereco;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnderecoMapper {
    EnderecoMapper MAPPER = Mappers.getMapper(EnderecoMapper.class);

    @Mapping(target = "cidade", expression = "java(cidade)")
    @Mapping(target = "id", ignore = true)
    Endereco enderecoDtoToEndereco(EnderecoDto enderecoDto, @Context Cidade cidade);

    default Set<Endereco> enderecoDtoToEnderecos(EnderecoDto enderecoDto, @Context Cidade cidade) {
        Set<Endereco> enderecos = new HashSet<>();
        enderecos.add(enderecoDtoToEndereco(enderecoDto, cidade));
        return enderecos;
    }

    default EnderecoDto ultimoEnderecoDto(Set<Endereco> enderecos) {
        return enderecoToEnderecoDto(enderecos.stream().findFirst().orElse(null));
    }

    Set<Endereco> enderecoDtoSetToEnderecoSet(Set<EnderecoDto> set);

    @Mapping(target = "cidId", source = "endereco.cidade.id")
    EnderecoDto enderecoToEnderecoDto(Endereco endereco);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEnderecoFromEnderecoDto(EnderecoDto enderecoDto, @MappingTarget Endereco endereco);
}
