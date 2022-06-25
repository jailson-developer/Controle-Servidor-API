package br.com.pjc.controle_servidor.modules.pessoa.model;

import br.com.pjc.controle_servidor.modules.pessoa.enums.ESexo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "pessoa")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa implements Serializable {
    @Id
    @Column(name = "pes_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pes_nome", length = 200)
    private String nome;
    @Column(name = "pes_data_nascimento")
    private LocalDate dataNascimento;
    @Column(name = "pes_sexo", length = 9)
    private ESexo sexo;
    @Column(name = "pes_mae", length = 200)
    private String mae;
    @Column(name = "pes_pai", length = 200)
    private String pai;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "pessoa_endereco", joinColumns = {@JoinColumn(name = "pes_id")}, inverseJoinColumns = {@JoinColumn(name = "end_id")})
    @OrderBy("id desc")
    private Set<Endereco> enderecos = new LinkedHashSet<>();
    @OneToMany
    @JoinColumn(name = "pes_id")
    private Set<FotoPessoa> fotos = new LinkedHashSet<>();
}