package br.com.pjc.controle_servidor.modules.s3;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
@Builder
public class MinioSendFile {
    private String bucket;
    private InputStream file;
    private String name;
    private String contentType;
}
