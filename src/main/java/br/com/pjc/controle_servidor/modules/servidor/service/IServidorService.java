package br.com.pjc.controle_servidor.modules.servidor.service;

public interface IServidorService<RequestDTO, ResponseDTO> {
    ResponseDTO salvar(RequestDTO request);

    ResponseDTO editar(Long id, RequestDTO request);

    ResponseDTO findById(Long id);

    void deleteById(Long id);
}
