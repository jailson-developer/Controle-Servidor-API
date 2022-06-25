package br.com.pjc.controle_servidor.modules.pessoa.model;

import br.com.pjc.controle_servidor.modules.cidade.model.Cidade;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "endereco")
public class Endereco extends PanacheEntityBase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_id", nullable = false)
    private Long id;
    @Column(name = "end_tipo_logradouro", length = 50)
    private String endTipoLogradouro;
    @Column(name = "end_logradouro", length = 200)
    private String endLogradouro;
    @Column(name = "end_numero", length = 10)
    private String endNumero;
    @Column(name = "end_bairro", length = 100)
    private String endBairro;
    @ManyToOne
    @JoinColumn(name = "cid_id", nullable = false)
    private Cidade cidade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(id, endereco.id) && Objects.equals(endTipoLogradouro, endereco.endTipoLogradouro) && Objects.equals(endLogradouro, endereco.endLogradouro) && Objects.equals(endNumero, endereco.endNumero) && Objects.equals(endBairro, endereco.endBairro) && Objects.equals(cidade, endereco.cidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, endTipoLogradouro, endLogradouro, endNumero, endBairro, cidade.getId());
    }
}