package br.com.pjc.controle_servidor.modules.servidor.service;

import br.com.pjc.controle_servidor.modules.cidade.repository.CidadeRepository;
import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.pessoa.mapper.EnderecoMapper;
import br.com.pjc.controle_servidor.modules.pessoa.model.Endereco;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorTemporarioRequestDTO;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorTemporarioResponseDTO;
import br.com.pjc.controle_servidor.modules.servidor.filtro.ServidorFiltro;
import br.com.pjc.controle_servidor.modules.servidor.mapper.ServidorMapper;
import br.com.pjc.controle_servidor.modules.servidor.model.ServidorTemporario;
import br.com.pjc.controle_servidor.modules.servidor.repository.ServidorTemporarioRepository;
import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ServidorTemporarioServiceImpl extends ServidorServiceBase<ServidorTemporarioRepository, ServidorTemporario, ServidorTemporarioRequestDTO, ServidorTemporarioResponseDTO> {
    @Inject
    CidadeRepository cidadeRepository;

    @Transactional
    @Override
    public ServidorTemporarioResponseDTO salvar(ServidorTemporarioRequestDTO servidor) {
        var cidade = cidadeRepository.findByIdOptional(servidor.getEndereco().getCidId()).orElseThrow(() -> new NotFoundException("Cidade não encontrada!"));
        var model = ServidorMapper.INSTANCE.servidorTemporarioDtoToServidor(servidor, cidade);
        repository.persistAndFlush(model);
        return ServidorMapper.INSTANCE.servidorToServidorTemporarioDto(model);
    }

    @Transactional
    @Override
    public ServidorTemporarioResponseDTO editar(Long id, ServidorTemporarioRequestDTO request) {
        var model = getById(id);
        ServidorMapper.INSTANCE.servidorTemporarioDtoToServidor(model, request);
        if (request.getEndereco().getId() > 0) {
            Endereco endereco = (Endereco) Endereco.findByIdOptional(request.getEndereco().getId()).orElseThrow();
            if (request.getEndereco().hashCode() != endereco.hashCode()) {
                var cidade = cidadeRepository.findByIdOptional(request.getEndereco().getCidId()).orElseThrow(() -> new NotFoundException("Cidade não encontrada!"));
                model.getEnderecos().add(EnderecoMapper.MAPPER.enderecoDtoToEndereco(request.getEndereco(), cidade));
            }
        }
        repository.persistAndFlush(model);

        //Ordena decrescente
        model.setEnderecos(model.getEnderecos().stream().sorted((o1, o2) -> o2.getId().compareTo(o1.getId())).collect(Collectors.toCollection(LinkedHashSet::new)));
        return ServidorMapper.INSTANCE.servidorToServidorTemporarioDto(model);
    }

    @Override
    public ServidorTemporarioResponseDTO findById(Long id) {
        return ServidorMapper.INSTANCE.servidorToServidorTemporarioDto(getById(id));
    }


    public PaginatedResponse<List<ServidorTemporarioResponseDTO>> findAll(PageRequest pageRequest, ServidorFiltro.ServidorTemporarioFiltro filtro) {
        var result = repository.findAll(pageRequest, filtro);
        return new PaginatedResponse<>(result.getTotalCount(), result.getRecords().stream().map(ServidorMapper.INSTANCE::servidorToServidorTemporarioDto)
                .collect(Collectors.toUnmodifiableList()));
    }
}
