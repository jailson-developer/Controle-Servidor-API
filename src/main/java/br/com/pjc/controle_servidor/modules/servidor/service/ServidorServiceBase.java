package br.com.pjc.controle_servidor.modules.servidor.service;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

public abstract class ServidorServiceBase<Repository extends PanacheRepository<Entity>, Entity, Request, Response> implements IServidorService<Request, Response> {
    @Inject
    public Repository repository;

    protected Entity getById(Long id) {
        return repository.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Registro não encontrado!"));
    }

    @Transactional
    public void deleteById(Long id) {
        repository.delete(getById(id));
    }
}
