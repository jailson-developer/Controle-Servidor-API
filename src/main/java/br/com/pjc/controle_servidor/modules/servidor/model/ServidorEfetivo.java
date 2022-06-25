package br.com.pjc.controle_servidor.modules.servidor.model;

import br.com.pjc.controle_servidor.modules.pessoa.model.Pessoa;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Getter
@Setter
@Entity(name = "servidor_efetivo")
@Table
public class ServidorEfetivo extends Pessoa {
    @Column(name = "se_matricula", length = 20)
    private String matricula;
}