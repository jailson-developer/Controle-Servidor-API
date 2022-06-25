package br.com.pjc.controle_servidor.modules.servidor.service;

import br.com.pjc.controle_servidor.modules.cidade.repository.CidadeRepository;
import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.pessoa.mapper.EnderecoMapper;
import br.com.pjc.controle_servidor.modules.pessoa.model.Endereco;
import br.com.pjc.controle_servidor.modules.servidor.dto.UnidadeRequestDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.UnidadeResponseDto;
import br.com.pjc.controle_servidor.modules.servidor.mapper.UnidadeMapper;
import br.com.pjc.controle_servidor.modules.servidor.repository.UnidadeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UnidadeServiceImpl implements IUnidadeService {
    @Inject
    UnidadeRepository repository;
    @Inject
    CidadeRepository cidadeRepository;

    @Override
    @Transactional
    public UnidadeResponseDto salvar(UnidadeRequestDto unidadeDto) {
        var cidade = cidadeRepository.findByIdOptional(unidadeDto.getEndereco().getCidId()).orElseThrow(() -> new NotFoundException("Cidade não encontrada!"));
        var unidade = UnidadeMapper.MAPPER.unidadeDtoToUnidade(unidadeDto, cidade);
        repository.persistAndFlush(unidade);
        return UnidadeMapper.MAPPER.unidadeToUnidadeDto(unidade);
    }

    @Override
    @Transactional
    public UnidadeResponseDto editar(Long id, UnidadeRequestDto unidadeDto) {
        var unidade = repository.buscarUnidade(id);
        UnidadeMapper.MAPPER.updateUnidadeFromUnidadeDto(unidadeDto, unidade);
        if (unidadeDto.getEndereco().getId() > 0) {
            Endereco endereco = (Endereco) Endereco.findByIdOptional(unidadeDto.getEndereco().getId()).orElseThrow();
            if (unidadeDto.getEndereco().hashCode() != endereco.hashCode()) {
                var cidade = cidadeRepository.findByIdOptional(unidadeDto.getEndereco().getCidId()).orElseThrow(() -> new NotFoundException("Cidade não encontrada!"));
                unidade.getEnderecos().add(EnderecoMapper.MAPPER.enderecoDtoToEndereco(unidadeDto.getEndereco(), cidade));
            }
        }
        repository.persistAndFlush(unidade);
        unidade.setEnderecos(unidade.getEnderecos().stream().sorted((o1, o2) -> o2.getId().compareTo(o1.getId())).collect(Collectors.toCollection(LinkedHashSet::new)));
        return UnidadeMapper.MAPPER.unidadeToUnidadeDto(unidade);
    }

    @Override
    public UnidadeResponseDto findById(Long id) {
        return UnidadeMapper.MAPPER.unidadeToUnidadeDto(repository.buscarUnidade(id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.delete(repository.buscarUnidade(id));
    }

    @Override
    public PaginatedResponse<List<UnidadeResponseDto>> findAll(PageRequest pageRequest, String nome, String sigla) {
        var result = repository.findAll(pageRequest, nome, sigla);
        return new PaginatedResponse<>(result.getTotalCount(), result.getRecords().stream().map(UnidadeMapper.MAPPER::unidadeToUnidadeDto)
                .collect(Collectors.toUnmodifiableList()));
    }
}
