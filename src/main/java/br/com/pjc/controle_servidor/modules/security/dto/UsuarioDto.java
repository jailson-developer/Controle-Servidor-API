package br.com.pjc.controle_servidor.modules.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class UsuarioDto implements Serializable {
    private String userName;
    private String userLogin;
    private  Set<RoleDto> roles;
}
