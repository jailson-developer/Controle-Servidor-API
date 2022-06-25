package br.com.pjc.controle_servidor.modules.servidor.dto;

import br.com.pjc.controle_servidor.modules.pessoa.dto.PessoaResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ServidorTemporarioResponseDTO extends PessoaResponseDTO {
    private LocalDate servidorDataAdmissao;
    private LocalDate servidorDataDemissao;
}
