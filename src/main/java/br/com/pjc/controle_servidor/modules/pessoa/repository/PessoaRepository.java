package br.com.pjc.controle_servidor.modules.pessoa.repository;

import br.com.pjc.controle_servidor.modules.core.Func;
import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.pessoa.dto.FiltroPessoaDTO;
import br.com.pjc.controle_servidor.modules.pessoa.model.Pessoa;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.util.List;

import static br.com.pjc.controle_servidor.modules.core.Func.formatarQuery;

@ApplicationScoped
public class PessoaRepository implements PanacheRepository<Pessoa> {
    public PaginatedResponse<List<Pessoa>> findAll(PageRequest pageRequest, FiltroPessoaDTO filtro) {
        final Parameters parameters = new Parameters();
        final StringBuilder query = new StringBuilder("1=1");
        if (Func.isNotNullOrEmpty(filtro.getNome())) {
            parameters.and("nome", formatarQuery(filtro.getNome()));
            query.append(" AND lower(nome) LIKE :nome");
        }
        if (Func.isNotNullOrEmpty(filtro.getMae())) {
            parameters.and("mae", formatarQuery(filtro.getMae()));
            query.append(" AND lower(mae) LIKE :mae");
        }
        if (Func.isNotNullOrEmpty(filtro.getPai())) {
            parameters.and("pai", formatarQuery(filtro.getPai()));
            query.append(" AND lower(pai) LIKE :pai");
        }
        return new PaginatedResponse<>(count(query.toString(), parameters), find(query.toString(), Sort.ascending("nome"), parameters)
                .page(pageRequest.getPage()).list());
    }
    public Pessoa buscarPessoa(Long id) {
        return findByIdOptional(id).orElseThrow(() -> new NotFoundException(String.format("Pessoa com id %d não encontrada!", id)));
    }
}
