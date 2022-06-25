package br.com.pjc.controle_servidor.modules.servidor.repository;

import br.com.pjc.controle_servidor.modules.core.Func;
import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.servidor.model.Unidade;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class UnidadeRepository implements PanacheRepository<Unidade> {
    public PaginatedResponse<List<Unidade>> findAll(PageRequest pageRequest, String nome, String sigla) {
        final Parameters parameters = new Parameters();
        final StringBuilder query = new StringBuilder("1=1");
        if (Func.isNotNullOrEmpty(nome)) {
            parameters.and("nome", Func.formatarQuery(nome));
            query.append(" AND lower(unidNome) LIKE :nome");
        }
        if (Func.isNotNullOrEmpty(sigla)) {
            parameters.and("sigla", Func.formatarQuery(sigla));
            query.append(" AND lower(unidSigla) LIKE :sigla");
        }
        return new PaginatedResponse<>(count(query.toString(), parameters), find(query.toString(), Sort.ascending("unidNome"), parameters)
                .page(pageRequest.getPage()).list());
    }

    public Unidade buscarUnidade(Long id) {
        return findByIdOptional(id).orElseThrow(() -> new NotFoundException(String.format("Unidade %d não encontrada!", id)));
    }
}
