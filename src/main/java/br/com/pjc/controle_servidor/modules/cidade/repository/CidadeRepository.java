package br.com.pjc.controle_servidor.modules.cidade.repository;

import br.com.pjc.controle_servidor.modules.cidade.model.Cidade;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CidadeRepository implements PanacheRepository<Cidade> {
}
