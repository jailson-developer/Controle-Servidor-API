package br.com.pjc.controle_servidor.modules.cidade.service;

import br.com.pjc.controle_servidor.modules.cidade.dto.CidadeDto;
import br.com.pjc.controle_servidor.modules.cidade.mapper.CidadeMapper;
import br.com.pjc.controle_servidor.modules.cidade.repository.CidadeRepository;
import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.pjc.controle_servidor.modules.core.Func.formatarQuery;

@ApplicationScoped
public class CidadeService {
    @Inject
    CidadeRepository repository;

    public PaginatedResponse<List<CidadeDto>> buscarCidades(String nome, PageRequest pageRequest) {
        var query = repository.find("lower(cidNome) LIKE ?1", formatarQuery(nome))
                .page(pageRequest.getPage());

        return new PaginatedResponse<>(query.count(), query.stream()
                .map(CidadeMapper.MAPPER::cidadeToCidadeDto)
                .collect(Collectors.toUnmodifiableList()));
    }
}
