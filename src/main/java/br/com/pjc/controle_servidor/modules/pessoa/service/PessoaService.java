package br.com.pjc.controle_servidor.modules.pessoa.service;

import br.com.pjc.controle_servidor.modules.cidade.repository.CidadeRepository;
import br.com.pjc.controle_servidor.modules.cidade.service.CidadeService;
import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.pessoa.dto.FiltroPessoaDTO;
import br.com.pjc.controle_servidor.modules.pessoa.dto.PessoaRequestDTO;
import br.com.pjc.controle_servidor.modules.pessoa.dto.PessoaResponseDTO;
import br.com.pjc.controle_servidor.modules.pessoa.mapper.EnderecoMapper;
import br.com.pjc.controle_servidor.modules.pessoa.mapper.PessoaMapper;
import br.com.pjc.controle_servidor.modules.pessoa.model.Endereco;
import br.com.pjc.controle_servidor.modules.pessoa.repository.PessoaRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class PessoaService {
    @Inject
    PessoaRepository repository;

    @Inject
    CidadeRepository cidadeRepository;


    public PessoaResponseDTO findById(Long id) {
        return PessoaMapper.INSTANCE.pessoaToPessoaDto(repository.buscarPessoa(id));
    }

    @Transactional
    public PessoaResponseDTO salvar(PessoaRequestDTO pessoaRequestDTO) {
        var cidade = cidadeRepository.findByIdOptional(pessoaRequestDTO.getEndereco().getCidId()).orElseThrow(() -> new NotFoundException("Cidade não encontrada!"));
        var pessoa = PessoaMapper.INSTANCE.pessoaDtoToPessoa(pessoaRequestDTO, cidade);
        repository.persistAndFlush(pessoa);
        return PessoaMapper.INSTANCE.pessoaToPessoaDto(pessoa);
    }

    @Transactional
    public PessoaResponseDTO editar(Long id, PessoaRequestDTO pessoaRequestDTO) {
        var pessoa = repository.buscarPessoa(id);
        //Se alterar alguma coisa no endereço cria um novo
        if (pessoaRequestDTO.getEndereco().getId() > 0) {
            Endereco endereco = (Endereco) Endereco.findByIdOptional(pessoaRequestDTO.getEndereco().getId()).orElseThrow();
            if (pessoaRequestDTO.getEndereco().hashCode() != endereco.hashCode()) {
                var cidade = cidadeRepository.findByIdOptional(pessoaRequestDTO.getEndereco().getCidId()).orElseThrow(() -> new NotFoundException("Cidade não encontrada!"));
                pessoa.getEnderecos().add(EnderecoMapper.MAPPER.enderecoDtoToEndereco(pessoaRequestDTO.getEndereco(), cidade));
            }
        }
        PessoaMapper.INSTANCE.updatePessoFromPessoaDto(pessoa, pessoaRequestDTO);
        repository.persistAndFlush(pessoa);
        //Ordena decrescente
        pessoa.setEnderecos(pessoa.getEnderecos().stream().sorted((o1, o2) -> o2.getId().compareTo(o1.getId())).collect(Collectors.toCollection(LinkedHashSet::new)));
        return PessoaMapper.INSTANCE.pessoaToPessoaDto(pessoa);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.delete(repository.buscarPessoa(id));
    }


    public PaginatedResponse<List<PessoaResponseDTO>> findAll(PageRequest pageRequest, FiltroPessoaDTO filtro) {
        var result = repository.findAll(pageRequest, filtro);
        return new PaginatedResponse<>(result.getTotalCount(), result.getRecords().stream().map(PessoaMapper.INSTANCE::pessoaToPessoaDto)
                .collect(Collectors.toUnmodifiableList()));
    }
}
