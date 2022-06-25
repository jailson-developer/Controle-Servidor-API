package br.com.pjc.controle_servidor.modules.servidor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeResponseDto extends UnidadeRequestDto {
    private Long id;
}
