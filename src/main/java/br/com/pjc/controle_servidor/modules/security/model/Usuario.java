package br.com.pjc.controle_servidor.modules.security.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "user_name", length = 80)
    private String userName;

    @Column(name = "user_login", length = 40)
    private String userLogin;

    @Column(name = "user_password", length = 250)
    private String userPassword;

    @ManyToMany
    @JoinTable(name = "role_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new LinkedHashSet<>();
}