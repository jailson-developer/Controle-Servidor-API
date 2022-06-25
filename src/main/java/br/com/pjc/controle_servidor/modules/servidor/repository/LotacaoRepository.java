package br.com.pjc.controle_servidor.modules.servidor.repository;

import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.servidor.filtro.ServidorFiltro;
import br.com.pjc.controle_servidor.modules.servidor.model.Lotacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class LotacaoRepository implements PanacheRepository<Lotacao> {
    public Lotacao buscarLotacao(Long id) {
        return findByIdOptional(id).orElseThrow(() -> new NotFoundException(String.format("Lotação %d não encontrada!", id)));
    }

    public PaginatedResponse<List<Lotacao>> findAll(PageRequest pageRequest, ServidorFiltro.LotacaoFiltro filtro) {
        var filter = filtro.filtroLotacao();
        return new PaginatedResponse<>(count(filter.getQuery().toString(), filter.getParameters()),
                find(filter.getQuery().toString(), Sort.ascending("pessoa.nome"), filter.getParameters()).page(pageRequest.getPage()).list());
    }
}
