package br.com.pjc.controle_servidor.modules.servidor.service;

import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.servidor.dto.LotacaoRequestDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.LotacaoResponseDto;
import br.com.pjc.controle_servidor.modules.servidor.filtro.ServidorFiltro;

import java.util.List;

public interface ILotacaoService {
    LotacaoResponseDto salvar(LotacaoRequestDto request);

    LotacaoResponseDto editar(Long id, LotacaoRequestDto request);

    LotacaoResponseDto findById(Long id);

    void deleteById(Long id);

    PaginatedResponse<List<LotacaoResponseDto>> findAll(PageRequest pageRequest, ServidorFiltro.LotacaoFiltro filtro);
}
