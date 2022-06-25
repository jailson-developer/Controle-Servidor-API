package br.com.pjc.controle_servidor.modules.servidor.repository;

import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.servidor.filtro.ServidorFiltro;
import br.com.pjc.controle_servidor.modules.servidor.model.ServidorTemporario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ServidorTemporarioRepository implements PanacheRepository<ServidorTemporario> {
    public PaginatedResponse<List<ServidorTemporario>> findAll(PageRequest pageRequest, ServidorFiltro.ServidorTemporarioFiltro servidorTemporarioFiltro) {
        var filtro = ServidorFiltro.filtroServidorTemporario(servidorTemporarioFiltro);
        return new PaginatedResponse<>(count(filtro.getQuery().toString(), filtro.getParameters()),
                find(filtro.getQuery().toString(), Sort.ascending("nome"), filtro.getParameters()).page(pageRequest.getPage()).list());
    }
}
