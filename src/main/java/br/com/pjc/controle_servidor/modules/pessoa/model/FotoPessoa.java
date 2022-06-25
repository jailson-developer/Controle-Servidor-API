package br.com.pjc.controle_servidor.modules.pessoa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "foto_pessoa")
@Getter
@Setter
public class FotoPessoa {
    @Id
    @Column(name = "fp_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fpId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    @Column(name = "fp_data")
    private LocalDate fpData;

    @Column(name = "fp_bucket", length = 50)
    private String fpBucket;

    @Column(name = "fp_hash", length = 50)
    private String fpHash;

    @PrePersist
    void prePersist() {
        fpData = LocalDate.now();
    }
}