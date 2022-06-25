package br.com.pjc.controle_servidor.modules.servidor.service;

import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.pessoa.repository.PessoaRepository;
import br.com.pjc.controle_servidor.modules.servidor.dto.LotacaoRequestDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.LotacaoResponseDto;
import br.com.pjc.controle_servidor.modules.servidor.filtro.ServidorFiltro;
import br.com.pjc.controle_servidor.modules.servidor.mapper.LotacaoMapper;
import br.com.pjc.controle_servidor.modules.servidor.repository.LotacaoRepository;
import br.com.pjc.controle_servidor.modules.servidor.repository.UnidadeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LotacaoServiceImpl implements ILotacaoService {
    @Inject
    LotacaoRepository repository;

    @Inject
    PessoaRepository pessoaRepository;

    @Inject
    UnidadeRepository unidadeRepository;

    @Transactional
    @Override
    public LotacaoResponseDto salvar(LotacaoRequestDto request) {
        var lotacao = LotacaoMapper.MAPPER.lotacaoDtoToLotacao(request);
        lotacao.setPessoa(pessoaRepository.buscarPessoa(request.getPessoaId()));
        lotacao.setUnidade(unidadeRepository.buscarUnidade(request.getUnidadeId()));
        repository.persistAndFlush(lotacao);
        return LotacaoMapper.MAPPER.lotacaoToLotacaoDto(lotacao);
    }

    @Transactional
    @Override
    public LotacaoResponseDto editar(Long id, LotacaoRequestDto request) {
        var lotacao = repository.buscarLotacao(id);
        LotacaoMapper.MAPPER.updateLotacaoFromLotacaoDto(request, lotacao);
        var unidade = unidadeRepository.buscarUnidade(request.getUnidadeId());
        lotacao.setUnidade(unidade);
        repository.persist(lotacao);
        return LotacaoMapper.MAPPER.lotacaoToLotacaoDto(lotacao);
    }

    @Override
    public LotacaoResponseDto findById(Long id) {
        return LotacaoMapper.MAPPER.lotacaoToLotacaoDto(repository.buscarLotacao(id));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.delete(repository.buscarLotacao(id));
    }

    @Override
    public PaginatedResponse<List<LotacaoResponseDto>> findAll(PageRequest pageRequest, ServidorFiltro.LotacaoFiltro filtro) {
        var result = repository.findAll(pageRequest, filtro);
        return new PaginatedResponse<>(result.getTotalCount(), result.getRecords().stream().map(LotacaoMapper.MAPPER::lotacaoToLotacaoDto)
                .collect(Collectors.toUnmodifiableList()));
    }


}
