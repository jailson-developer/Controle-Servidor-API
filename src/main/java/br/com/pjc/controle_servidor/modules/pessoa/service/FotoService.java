package br.com.pjc.controle_servidor.modules.pessoa.service;

import br.com.pjc.controle_servidor.modules.core.IdDto;
import br.com.pjc.controle_servidor.modules.pessoa.dto.FotoRequestDto;
import br.com.pjc.controle_servidor.modules.pessoa.dto.FotoResponseDTO;
import br.com.pjc.controle_servidor.modules.pessoa.model.FotoPessoa;
import br.com.pjc.controle_servidor.modules.pessoa.repository.FotoRepository;
import br.com.pjc.controle_servidor.modules.pessoa.repository.PessoaRepository;
import br.com.pjc.controle_servidor.modules.s3.MinioSendFile;
import br.com.pjc.controle_servidor.modules.s3.MinioService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FotoService implements IFotoService {
    @Inject
    FotoRepository repository;
    @Inject
    MinioService minioService;

    @Inject
    PessoaRepository pessoaRepository;

    @ConfigProperty(name = "minio.bucket-name")
    String bucketName;

    @Transactional
    @Override
    public IdDto uploadFoto(Long pessoaId, FotoRequestDto fotoRequestDto) {
        if (fotoRequestDto.getFoto() == null)
            throw new ValidationException("Foto não informada");
        var extensao = fotoRequestDto.getFileName().substring(fotoRequestDto.getFileName().indexOf("."));
        fotoRequestDto.setFileName(UUID.randomUUID().toString().concat(extensao));
        minioService.enviarArquivo(MinioSendFile.builder()
                .bucket(bucketName)
                .file(fotoRequestDto.getFoto())
                .name(fotoRequestDto.getFileName())
                .contentType(fotoRequestDto.getContentType())
                .build());
        FotoPessoa pessoa = new FotoPessoa();
        pessoa.setFpBucket(bucketName);
        pessoa.setFpHash(fotoRequestDto.getFileName());
        pessoa.setPessoa(pessoaRepository.findByIdOptional(pessoaId).orElseThrow(() -> new NotFoundException("Pessoa/Servidor não encontrada")));
        repository.persist(pessoa);
        return new IdDto(pessoa.getFpId());
    }

    @Override
    public List<FotoResponseDTO> buscarFotos(Long pessoaId) {
        List<FotoResponseDTO> fotos = new ArrayList<>();
        var pessoa = pessoaRepository.findByIdOptional(pessoaId).orElseThrow(() -> new NotFoundException("Pessoa não encontrada!"));
        pessoa.getFotos().forEach(foto -> fotos.add(minioService.buscarArquivo(foto.getFpBucket(), foto.getFpHash())));
        return fotos;
    }

    @Override
    public FotoResponseDTO buscarFoto(Long fotoId) {
        var foto = repository.findByIdOptional(fotoId).orElseThrow(() -> new NotFoundException("Foto não encontrada"));
        return minioService.buscarArquivo(foto.getFpBucket(), foto.getFpHash());
    }
}
