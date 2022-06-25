package br.com.pjc.controle_servidor.modules.core;

import io.quarkus.panache.common.Page;
import lombok.Setter;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@Setter
public class PageRequest {
    @QueryParam("page")
    @DefaultValue("0")
    private int page;
    @QueryParam("size")
    @DefaultValue("20")
    private int size;

    public Page getPage() {
        return Page.of(page, size);
    }
}
