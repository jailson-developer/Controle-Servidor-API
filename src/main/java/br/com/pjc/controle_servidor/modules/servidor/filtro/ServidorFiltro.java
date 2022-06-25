package br.com.pjc.controle_servidor.modules.servidor.filtro;

import br.com.pjc.controle_servidor.modules.core.Func;
import br.com.pjc.controle_servidor.modules.pessoa.dto.FiltroPessoaDTO;
import io.quarkus.panache.common.Parameters;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.QueryParam;
import java.time.LocalDate;

import static br.com.pjc.controle_servidor.modules.core.Func.formatarQuery;


public class ServidorFiltro {


    public static Filter filtroServidorEfetivo(ServidorEfetivoFiltro filtro) {
        var result = filtroPessoa(filtro);
        if (Func.isNotNullOrEmpty(filtro.getMatricula())) {
            result.getParameters().and("matricula", formatarQuery(filtro.getMatricula()));
            result.getQuery().append(" AND lower(matricula) LIKE :matricula");
        }
        return result;
    }

    public static Filter filtroServidorTemporario(ServidorTemporarioFiltro filtro) {
        var result = filtroPessoa(filtro);
        if (filtro.getAdmissao() != null) {
            result.getParameters().and("admissao", filtro.getAdmissao());
            result.getQuery().append(" AND servidorDataAdmissao = :admissao");
        }
        if (filtro.getDemissao() != null) {
            result.getParameters().and("demissao", filtro.getDemissao());
            result.getQuery().append(" AND servidorDataDemissao = :demissao");
        }
        return result;
    }


    private static Filter filtroPessoa(FiltroPessoaDTO filtro) {
        final Parameters parameters = new Parameters();
        final StringBuilder query = new StringBuilder("1=1");
        if (Func.isNotNullOrEmpty(filtro.getNome())) {
            parameters.and("nome", formatarQuery(filtro.getNome()));
            query.append(" AND lower(nome) LIKE :nome");
        }
        if (Func.isNotNullOrEmpty(filtro.getMae())) {
            parameters.and("mae", formatarQuery(filtro.getMae()));
            query.append(" AND lower(mae) LIKE :mae");
        }
        if (Func.isNotNullOrEmpty(filtro.getPai())) {
            parameters.and("pai", formatarQuery(filtro.getPai()));
            query.append(" AND lower(pai) LIKE :pai");
        }
        return new Filter(parameters, query);
    }


    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = true)
    public static class ServidorEfetivoFiltro extends FiltroPessoaDTO {
        @QueryParam("matricula")
        private String matricula;

        @Override
        public boolean ehVazio() {
            return hashCode() == new ServidorEfetivoFiltro().hashCode();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = true)
    public static class ServidorTemporarioFiltro extends FiltroPessoaDTO {
        @QueryParam("admissao")
        private LocalDate admissao;
        @QueryParam("demissao")
        private LocalDate demissao;

        @Override
        public boolean ehVazio() {
            return hashCode() == new ServidorTemporarioFiltro().hashCode();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = true)
    public static class LotacaoFiltro extends FiltroPessoaDTO {
        @QueryParam("portaria")
        String portaria;
        @QueryParam("dataLotacao")
        LocalDate dataLotacao;
        @QueryParam("dataRemocao")
        LocalDate dataRemocao;

        @Override
        public boolean ehVazio() {
            return hashCode() == new LotacaoFiltro().hashCode();
        }

        public Filter filtroLotacao() {
            final Parameters parameters = new Parameters();
            final StringBuilder query = new StringBuilder("1=1");
            if (Func.isNotNullOrEmpty(getNome())) {
                parameters.and("nome", formatarQuery(getNome()));
                query.append(" AND lower(pessoa.nome) LIKE :nome");
            }
            if (Func.isNotNullOrEmpty(getMae())) {
                parameters.and("mae", formatarQuery(getMae()));
                query.append(" AND lower(pessoa.mae) LIKE :mae");
            }
            if (Func.isNotNullOrEmpty(getPai())) {
                parameters.and("pai", formatarQuery(getPai()));
                query.append(" AND lower(pessoa.pai) LIKE :pai");
            }
            if (Func.isNotNullOrEmpty(getPortaria())) {
                parameters.and("portaria", formatarQuery(getPortaria()));
                query.append(" AND lower(portaria) LIKE :portaria");
            }
            if (getDataLotacao() != null) {
                parameters.and("dataLotacao", getDataLotacao());
                query.append(" AND dataLotacao = :dataLotacao");
            }
            if (getDataRemocao() != null) {
                parameters.and("dataRemocao", getDataRemocao());
                query.append(" AND dataRemocao = :dataRemocao");
            }
            return new Filter(parameters, query);
        }
    }

    @Getter
    @Setter
    public static class Filter {
        private final Parameters parameters;
        private final StringBuilder query;

        public Filter(Parameters parameters, StringBuilder query) {
            this.parameters = parameters;
            this.query = query;
        }
    }
}
