package br.com.pjc.controle_servidor.modules.cidade.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CidadeDto implements Serializable {
    private final Long id;
    private final String cidNome;
    private final String cidUf;
}
