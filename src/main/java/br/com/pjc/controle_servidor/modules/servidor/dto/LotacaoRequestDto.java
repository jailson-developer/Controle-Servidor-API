package br.com.pjc.controle_servidor.modules.servidor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LotacaoRequestDto implements Serializable {
    private Long pessoaId;
    private Long unidadeId;
    private LocalDate dataLotacao;
    private LocalDate dataRemocao;
    private String portaria;
}
