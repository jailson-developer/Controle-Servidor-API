package br.com.pjc.controle_servidor.modules.core;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;

@Provider
public class DateParamConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.isAssignableFrom(LocalDate.class)) {
            return (ParamConverter<T>) new DateParamConverter();
        }
        return null;
    }
}
