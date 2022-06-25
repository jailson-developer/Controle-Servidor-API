package br.com.pjc.controle_servidor.modules.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorResponseBody {
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
