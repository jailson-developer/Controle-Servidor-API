package br.com.pjc.controle_servidor.modules.servidor.service;

import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.servidor.dto.UnidadeRequestDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.UnidadeResponseDto;

import java.util.List;

public interface IUnidadeService {
    UnidadeResponseDto salvar(UnidadeRequestDto request);

    UnidadeResponseDto editar(Long id, UnidadeRequestDto request);

    UnidadeResponseDto findById(Long id);

    void deleteById(Long id);

    PaginatedResponse<List<UnidadeResponseDto>> findAll(PageRequest pageRequest, String nome, String sigla);
}
