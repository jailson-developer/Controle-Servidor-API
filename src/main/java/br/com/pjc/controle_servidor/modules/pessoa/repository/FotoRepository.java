package br.com.pjc.controle_servidor.modules.pessoa.repository;

import br.com.pjc.controle_servidor.modules.pessoa.model.FotoPessoa;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FotoRepository implements PanacheRepository<FotoPessoa> {
}
