package br.com.pjc.controle_servidor.modules.security.repository;

import br.com.pjc.controle_servidor.modules.security.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
    public Optional<Usuario> findUsuario(String usuario, String senha) {
        return find("userLogin = ?1 and userPassword = ?2", usuario, senha).firstResultOptional();
    }
}
