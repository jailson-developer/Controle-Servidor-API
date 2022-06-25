package br.com.pjc.controle_servidor.modules.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginatedResponse<T> {
    long totalCount;
    T records;

    public PaginatedResponse(long totalCount, T records) {
        this.totalCount = totalCount;
        this.records = records;
    }
}
