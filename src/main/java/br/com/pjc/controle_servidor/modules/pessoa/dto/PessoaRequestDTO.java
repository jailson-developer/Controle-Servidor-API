package br.com.pjc.controle_servidor.modules.pessoa.dto;

import br.com.pjc.controle_servidor.modules.pessoa.enums.ESexo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRequestDTO implements Serializable {
    @Schema(description = "Nome da Pessoa", maxLength = 100)
    @NotEmpty
    private String nome;
    @Schema(description = "Data de Nascimento")
    private LocalDate dataNascimento;
    @Schema(description = "Sexo")
    private ESexo sexo;
    @Schema(description = "Nome da mãe", maxLength = 200)
    private String mae;
    @Schema(description = "Nome do pai", maxLength = 200)
    private String pai;
    @Valid
    @NotNull
    private EnderecoDto endereco;
}
