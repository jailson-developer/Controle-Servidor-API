package br.com.pjc.controle_servidor.modules.core;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.Provider;
import java.time.LocalDate;

@Provider
public class DateParamConverter implements ParamConverter<LocalDate> {
    @Override
    public LocalDate fromString(String value) {
        return LocalDate.parse(value);
    }

    @Override
    public String toString(LocalDate value) {
        return value.toString();
    }
}
