package br.com.pjc.controle_servidor.modules.cidade.mapper;

import br.com.pjc.controle_servidor.modules.cidade.dto.CidadeDto;
import br.com.pjc.controle_servidor.modules.cidade.model.Cidade;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CidadeMapper {
    CidadeMapper MAPPER = Mappers.getMapper(CidadeMapper.class);

    Cidade cidadeDtoToCidade(CidadeDto cidadeDto);

    CidadeDto cidadeToCidadeDto(Cidade cidade);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCidadeFromCidadeDto(CidadeDto cidadeDto, @MappingTarget Cidade cidade);
}
