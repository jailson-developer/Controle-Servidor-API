package br.com.pjc.controle_servidor.modules.pessoa.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.PartType;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FotoRequestDto {
    @FormParam("foto")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream foto;
    @JsonIgnore
    private String contentType;
    @JsonIgnore
    private String fileName;
    @JsonIgnore
    private Logger logger = Logger.getLogger(FotoRequestDto.class);

    public FotoRequestDto(MultipartFormDataInput input) {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        if (uploadForm.get("foto").size() <= 0)
            return;
        InputPart inputPart = uploadForm.get("foto").get(0);
        try {
            MultivaluedMap<String, String> header = inputPart.getHeaders();
            fileName = getFileName(header);
            contentType = header.getFirst("Content-Type");
            foto = inputPart.getBody(InputStream.class, null);
        } catch (IOException e) {
            logger.errorf("Falha ao recuperar foto {0}", e);
        }
    }


    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                return name[1].trim().replaceAll("\"", "");
            }
        }
        return "unknown";
    }
}
