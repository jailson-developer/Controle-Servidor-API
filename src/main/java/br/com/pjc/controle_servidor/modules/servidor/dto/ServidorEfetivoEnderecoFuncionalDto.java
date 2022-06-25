package br.com.pjc.controle_servidor.modules.servidor.dto;

import br.com.pjc.controle_servidor.modules.pessoa.dto.EnderecoDto;
import br.com.pjc.controle_servidor.modules.pessoa.mapper.EnderecoMapper;
import br.com.pjc.controle_servidor.modules.pessoa.model.Pessoa;
import br.com.pjc.controle_servidor.modules.servidor.mapper.ServidorMapper;
import br.com.pjc.controle_servidor.modules.servidor.model.Lotacao;
import br.com.pjc.controle_servidor.modules.servidor.model.ServidorEfetivo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServidorEfetivoEnderecoFuncionalDto {
    private EnderecoDto enderecoFuncional;
    private ServidorEfetivoResponseDTO servidor;

    public ServidorEfetivoEnderecoFuncionalDto(Pessoa pessoa, Lotacao lotacao) {
        servidor = ServidorMapper.INSTANCE.servidorEfetivoToServidorEfetivoDto((ServidorEfetivo) pessoa);
        enderecoFuncional = EnderecoMapper.MAPPER.enderecoToEnderecoDto(lotacao.getUnidade().getEnderecos().stream().findFirst().orElse(null));
    }
}
