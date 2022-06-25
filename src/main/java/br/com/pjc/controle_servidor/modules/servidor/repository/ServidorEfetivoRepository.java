package br.com.pjc.controle_servidor.modules.servidor.repository;

import br.com.pjc.controle_servidor.modules.core.PageRequest;
import br.com.pjc.controle_servidor.modules.core.PaginatedResponse;
import br.com.pjc.controle_servidor.modules.servidor.filtro.ServidorFiltro.ServidorEfetivoFiltro;
import br.com.pjc.controle_servidor.modules.servidor.model.ServidorEfetivo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

import static br.com.pjc.controle_servidor.modules.servidor.filtro.ServidorFiltro.filtroServidorEfetivo;

@ApplicationScoped
public class ServidorEfetivoRepository implements PanacheRepository<ServidorEfetivo> {
    public PaginatedResponse<List<ServidorEfetivo>> findAll(PageRequest pageRequest, ServidorEfetivoFiltro filtroPessoaDTO) {
        var filtro = filtroServidorEfetivo(filtroPessoaDTO);
        return new PaginatedResponse<>(count(filtro.getQuery().toString(), filtro.getParameters()),
                find(filtro.getQuery().toString(), Sort.ascending("nome"), filtro.getParameters()).page(pageRequest.getPage()).list());
    }
}
