package br.com.pjc.controle_servidor.modules.pessoa.service;

import br.com.pjc.controle_servidor.modules.core.IdDto;
import br.com.pjc.controle_servidor.modules.pessoa.dto.FotoRequestDto;
import br.com.pjc.controle_servidor.modules.pessoa.dto.FotoResponseDTO;

import java.util.List;

public interface IFotoService {
    IdDto uploadFoto(Long pessoaId, FotoRequestDto fotoRequestDto);

    List<FotoResponseDTO> buscarFotos(Long pessoaId);

    FotoResponseDTO buscarFoto(Long fotoId);
}
