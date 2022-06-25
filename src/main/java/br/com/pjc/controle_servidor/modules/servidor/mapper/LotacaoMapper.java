package br.com.pjc.controle_servidor.modules.servidor.mapper;

import br.com.pjc.controle_servidor.modules.pessoa.mapper.PessoaMapper;
import br.com.pjc.controle_servidor.modules.servidor.dto.LotacaoRequestDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.LotacaoResponseDto;
import br.com.pjc.controle_servidor.modules.servidor.model.Lotacao;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PessoaMapper.class, UnidadeMapper.class})
public interface LotacaoMapper {
    LotacaoMapper MAPPER = Mappers.getMapper(LotacaoMapper.class);

    Lotacao lotacaoDtoToLotacao(LotacaoRequestDto lotacaoDto);

    LotacaoResponseDto lotacaoToLotacaoDto(Lotacao lotacao);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLotacaoFromLotacaoDto(LotacaoRequestDto lotacaoDto, @MappingTarget Lotacao lotacao);
}
