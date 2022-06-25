package br.com.pjc.controle_servidor.modules.servidor.model;

import br.com.pjc.controle_servidor.modules.pessoa.model.Endereco;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "unidade")
@Getter
@Setter
public class Unidade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unid_id", nullable = false)
    private Long id;

    @Column(name = "unid_nome", length = 200)
    private String unidNome;

    @Column(name = "unid_sigla", length = 20)
    private String unidSigla;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "unidade_endereco",
            joinColumns = @JoinColumn(name = "unid_id"),
            inverseJoinColumns = @JoinColumn(name = "end_id"))
    @OrderBy("id DESC")
    private Set<Endereco> enderecos = new LinkedHashSet<>();
}