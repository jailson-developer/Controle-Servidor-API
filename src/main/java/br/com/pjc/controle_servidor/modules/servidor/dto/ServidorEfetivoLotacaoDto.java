package br.com.pjc.controle_servidor.modules.servidor.dto;

import br.com.pjc.controle_servidor.modules.pessoa.dto.FotoResponseDTO;
import br.com.pjc.controle_servidor.modules.pessoa.model.Pessoa;
import br.com.pjc.controle_servidor.modules.servidor.model.Lotacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ServidorEfetivoLotacaoDto {
    private String nome;
    private Integer idade;
    private String lotacao;
    private List<FotoResponseDTO> fotos = new ArrayList<>();
    @JsonIgnore
    private Long pessoaId;

    public ServidorEfetivoLotacaoDto(Pessoa pessoa, Lotacao lotacao) {
        nome = pessoa.getNome();
        pessoaId = pessoa.getId();
        this.lotacao = lotacao.getUnidade().getUnidNome();
        if (pessoa.getDataNascimento() != null) {
            idade = Period.between(pessoa.getDataNascimento(), LocalDate.now()).getYears();
        }
    }
}
