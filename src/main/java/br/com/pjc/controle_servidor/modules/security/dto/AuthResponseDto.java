package br.com.pjc.controle_servidor.modules.security.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthResponseDto {
    private String access_token;
    private String refresh_token;
    private Long expires_in;
    private Long refresh_expires_in;
}