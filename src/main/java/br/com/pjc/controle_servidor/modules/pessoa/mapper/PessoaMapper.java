package br.com.pjc.controle_servidor.modules.pessoa.mapper;

import br.com.pjc.controle_servidor.modules.cidade.model.Cidade;
import br.com.pjc.controle_servidor.modules.pessoa.dto.PessoaRequestDTO;
import br.com.pjc.controle_servidor.modules.pessoa.dto.PessoaResponseDTO;
import br.com.pjc.controle_servidor.modules.pessoa.model.Pessoa;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {EnderecoMapper.class})
public interface PessoaMapper {
    PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

    @Mapping(target = "enderecos", source = "endereco")
    Pessoa pessoaDtoToPessoa(PessoaRequestDTO pessoaDto, @Context Cidade cidade);

    @Mapping(target = "enderecos", ignore = true)
    void updatePessoFromPessoaDto(@MappingTarget Pessoa pessoa, PessoaRequestDTO pessoaResponseDTO);

    @Mapping(source = "enderecos", target = "endereco")
    PessoaResponseDTO pessoaToPessoaDto(Pessoa pessoa);
}
