package br.com.pjc.controle_servidor.modules.pessoa.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FotoResponseDTO {
    private String url;
}
