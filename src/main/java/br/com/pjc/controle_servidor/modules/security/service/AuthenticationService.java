package br.com.pjc.controle_servidor.modules.security.service;

import br.com.pjc.controle_servidor.modules.security.dto.AuthResponseDto;
import br.com.pjc.controle_servidor.modules.security.repository.UsuarioRepository;
import br.com.pjc.controle_servidor.modules.security.utils.TokenManager;
import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class AuthenticationService {
    @Inject
    UsuarioRepository repository;

    @Inject
    TokenManager tokenManager;

    @SneakyThrows
    public AuthResponseDto login(String login, String password) {
        var user = repository.findUsuario(login, password).orElseThrow(() -> new NotFoundException("Usuário ou senha inválido"));
        return tokenManager.generateToken(user);
    }

    public AuthResponseDto refreshToken(Long  userId) {
        return tokenManager.generateToken(repository.findById(userId));
    }
}
