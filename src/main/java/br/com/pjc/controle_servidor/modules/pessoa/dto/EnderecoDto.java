package br.com.pjc.controle_servidor.modules.pessoa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EnderecoDto implements Serializable {
    private long id;
    private String endTipoLogradouro;
    @NotNull
    private String endLogradouro;
    @NotNull
    private String endNumero;
    @NotNull
    private String endBairro;
    @NotNull
    @Positive
    private Long cidId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnderecoDto that = (EnderecoDto) o;
        return id == that.id && Objects.equals(endTipoLogradouro, that.endTipoLogradouro) && Objects.equals(endLogradouro, that.endLogradouro) && Objects.equals(endNumero, that.endNumero) && Objects.equals(endBairro, that.endBairro) && Objects.equals(cidId, that.cidId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, endTipoLogradouro, endLogradouro, endNumero, endBairro, cidId);
    }
}
