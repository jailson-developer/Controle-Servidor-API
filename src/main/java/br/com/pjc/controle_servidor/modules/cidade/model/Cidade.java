package br.com.pjc.controle_servidor.modules.cidade.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cidade")
@Getter
@Setter
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid_id", nullable = false)
    private Long id;

    @Column(name = "cid_nome", length = 200)
    private String cidNome;

    @Column(name = "cid_uf", length = 2)
    private String cidUf;
}