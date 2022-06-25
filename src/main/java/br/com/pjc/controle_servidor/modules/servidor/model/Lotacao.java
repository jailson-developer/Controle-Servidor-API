package br.com.pjc.controle_servidor.modules.servidor.model;

import br.com.pjc.controle_servidor.modules.pessoa.model.Pessoa;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "lotacao")
@Getter
@Setter
public class Lotacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lot_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unid_id")
    private Unidade unidade;

    @Column(name = "lot_data_lotacao")
    private LocalDate dataLotacao;

    @Column(name = "lot_data_remocao")
    private LocalDate dataRemocao;

    @Column(name = "lot_portaria", length = 100)
    private String portaria;
}