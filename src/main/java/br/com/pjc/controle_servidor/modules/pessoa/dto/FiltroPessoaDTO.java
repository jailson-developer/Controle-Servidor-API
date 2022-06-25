package br.com.pjc.controle_servidor.modules.pessoa.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.QueryParam;

@EqualsAndHashCode
@Getter
@Setter
public class FiltroPessoaDTO {
    @QueryParam("nome")
    String nome;
    @QueryParam("mae")
    String mae;
    @QueryParam("pai")
    String pai;

    public boolean ehVazio() {
        return hashCode() == new FiltroPessoaDTO().hashCode();
    }
}
