package br.com.pjc.controle_servidor.modules.servidor.service;

import br.com.pjc.controle_servidor.modules.cidade.repository.CidadeRepository;
import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.pessoa.mapper.EnderecoMapper;
import br.com.pjc.controle_servidor.modules.pessoa.model.Endereco;
import br.com.pjc.controle_servidor.modules.pessoa.service.IFotoService;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorEfetivoEnderecoFuncionalDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorEfetivoLotacaoDto;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorEfetivoRequestDTO;
import br.com.pjc.controle_servidor.modules.servidor.dto.ServidorEfetivoResponseDTO;
import br.com.pjc.controle_servidor.modules.servidor.filtro.ServidorFiltro.ServidorEfetivoFiltro;
import br.com.pjc.controle_servidor.modules.servidor.mapper.ServidorMapper;
import br.com.pjc.controle_servidor.modules.servidor.model.ServidorEfetivo;
import br.com.pjc.controle_servidor.modules.servidor.repository.ServidorEfetivoRepository;
import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ServidorEfetivoServiceImpl extends ServidorServiceBase<ServidorEfetivoRepository, ServidorEfetivo, ServidorEfetivoRequestDTO, ServidorEfetivoResponseDTO> {

    @Inject
    IFotoService fotoService;
    @Inject
    CidadeRepository cidadeRepository;

    @Transactional
    @Override
    public ServidorEfetivoResponseDTO salvar(ServidorEfetivoRequestDTO servidor) {
        if (repository.find("matricula = ?1", servidor.getMatricula()).firstResultOptional().isPresent())
            throw new ValidationException("Servidor já cadastrado");
        var cidade = cidadeRepository.findByIdOptional(servidor.getEndereco().getCidId()).orElseThrow(() -> new NotFoundException("Cidade não encontrada!"));
        var model = ServidorMapper.INSTANCE.servidorEfetivoDtoToServidorEfetivo(servidor, cidade);
        repository.persistAndFlush(model);
        return ServidorMapper.INSTANCE.servidorEfetivoToServidorEfetivoDto(model);
    }

    @Transactional
    @Override
    public ServidorEfetivoResponseDTO editar(Long id, ServidorEfetivoRequestDTO request) {
        var model = getById(id);
        ServidorMapper.INSTANCE.updateServidorEfetivoFromServidorEfetivoDto(request, model);
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
        return ServidorMapper.INSTANCE.servidorEfetivoToServidorEfetivoDto(model);
    }

    @Override
    public ServidorEfetivoResponseDTO findById(Long id) {
        return ServidorMapper.INSTANCE.servidorEfetivoToServidorEfetivoDto(getById(id));
    }


    public PaginatedResponse<List<ServidorEfetivoResponseDTO>> findAll(PageRequest pageRequest, ServidorEfetivoFiltro filtro) {
        var result = repository.findAll(pageRequest, filtro);
        return new PaginatedResponse<>(result.getTotalCount(), result.getRecords().stream().map(ServidorMapper.INSTANCE::servidorEfetivoToServidorEfetivoDto)
                .collect(Collectors.toUnmodifiableList()));
    }

    public PaginatedResponse<List<ServidorEfetivoLotacaoDto>> servidoresPorUnidade(Long unidadeId, PageRequest pageRequest) {
        var em = repository.getEntityManager();
        String baseQuery = " FROM Pessoa se join Lotacao lo on lo.pessoa.id = se.id where lo.unidade.id = ?1";
        var totalCount = em.createQuery("select count(*)".concat(baseQuery), Long.class).setParameter(1, unidadeId).getSingleResult();

        var servidores = repository.getEntityManager()
                .createQuery("select new br.com.pjc.controle_servidor.modules.servidor.dto.ServidorEfetivoLotacaoDto(se, lo)".concat(baseQuery), ServidorEfetivoLotacaoDto.class)
                .setParameter(1, unidadeId)
                .setFirstResult(pageRequest.getPage().index)
                .setMaxResults(pageRequest.getPage().size).getResultList();
        servidores.forEach(servidor -> servidor.setFotos(fotoService.buscarFotos(servidor.getPessoaId())));

        return new PaginatedResponse<>(totalCount, servidores);
    }

    public PaginatedResponse<List<ServidorEfetivoEnderecoFuncionalDto>> enderecoFuncional(String nomeServidor, PageRequest pageRequest) {
        nomeServidor = "%".concat(nomeServidor.concat("%").toLowerCase());
        var em = repository.getEntityManager();
        String baseQuery = " FROM Pessoa se join Lotacao lo on lo.pessoa.id = se.id where lo.dataRemocao is null and lower(se.nome) LIKE :nome";
        var totalCount = em.createQuery("select count(*)".concat(baseQuery), Long.class)
                .setParameter("nome", nomeServidor).getSingleResult();

        var servidores = repository.getEntityManager()
                .createQuery("select new br.com.pjc.controle_servidor.modules.servidor.dto.ServidorEfetivoEnderecoFuncionalDto(se, lo)".concat(baseQuery),
                        ServidorEfetivoEnderecoFuncionalDto.class)
                .setParameter("nome", nomeServidor)
                .setFirstResult(pageRequest.getPage().index)
                .setMaxResults(pageRequest.getPage().size).getResultList();
        return new PaginatedResponse<>(totalCount, servidores);
    }
}
