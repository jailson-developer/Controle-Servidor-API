package br.com.pjc.controle_servidor.modules.servidor.dto;

import br.com.pjc.controle_servidor.modules.pessoa.dto.EnderecoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeRequestDto implements Serializable {
    private  String unidNome;
    private  String unidSigla;
    private  EnderecoDto endereco;
}
