package br.com.pjc.controle_servidor.modules.servidor.mapper;

import br.com.pjc.controle_servidor.modules.cidade.model.Cidade;
import br.com.pjc.controle_servidor.modules.pessoa.mapper.EnderecoMapper;
import br.com.pjc.controle_servidor.modules.servidor.dto.UnidadeRequestDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.UnidadeResponseDto;
import br.com.pjc.controle_servidor.modules.servidor.model.Unidade;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = EnderecoMapper.class)
public interface UnidadeMapper {
    UnidadeMapper MAPPER = Mappers.getMapper(UnidadeMapper.class);

    @Mapping(source = "endereco", target = "enderecos")
    Unidade unidadeDtoToUnidade(UnidadeRequestDto unidadeDto, @Context Cidade cidade);
    @Mapping(source = "enderecos", target = "endereco")
    UnidadeResponseDto unidadeToUnidadeDto(Unidade unidade);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "enderecos", ignore = true)
    void updateUnidadeFromUnidadeDto(UnidadeRequestDto unidadeDto, @MappingTarget Unidade unidade);
}
