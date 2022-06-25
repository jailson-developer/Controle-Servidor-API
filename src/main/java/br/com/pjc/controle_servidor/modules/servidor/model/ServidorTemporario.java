package br.com.pjc.controle_servidor.modules.servidor.model;

import br.com.pjc.controle_servidor.modules.pessoa.model.Pessoa;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity(name = "servidor_temporario")
@Getter
@Setter
public class ServidorTemporario extends Pessoa {
    @Column(name = "st_data_admissao")
    private LocalDate servidorDataAdmissao;
    @Column(name = "st_data_demissao")
    private LocalDate servidorDataDemissao;
}