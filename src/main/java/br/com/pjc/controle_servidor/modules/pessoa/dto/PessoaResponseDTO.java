package br.com.pjc.controle_servidor.modules.pessoa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponseDTO extends PessoaRequestDTO {
    private  Long id;
}
