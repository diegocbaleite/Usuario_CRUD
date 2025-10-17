package com.teste.usuario.service;

import com.teste.usuario.dto.UsuarioDTO;
import com.teste.usuario.model.Usuario;
import com.teste.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    // BUSCAR (GET por ID)
    public Optional<UsuarioDTO> buscar(Long id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    // CRIAR (INSERT)
    public UsuarioDTO criar(UsuarioDTO dto) {
        // Regra 1: CPF único
        if (repository.existsByCpf(dto.getCpf())) {
            System.out.println("Erro: CPF já cadastrado - " + dto.getCpf());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "CPF já cadastrado"
            );
        }

        // Regra 2: E-mail único
        if (repository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail já cadastrado"
            );
        }

        // Regra 3: Idade mínima de 18 anos
        if (dto.getIdade() < 18) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Idade mínima para cadastro é 18 anos"
            );
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setCpf(dto.getCpf());
        usuario.setIdade(dto.getIdade());

        Usuario salvo = repository.save(usuario);
        return toDTO(salvo);
    }

    // ATUALIZAR (UPDATE)
    public UsuarioDTO atualizar(Long id, UsuarioDTO dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        // Regra 4: Impedir troca de CPF
        if (!usuario.getCpf().equals(dto.getCpf())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O CPF não pode ser alterado"
            );
        }

        // Regra 5: Impedir e-mail duplicado
        if (!usuario.getEmail().equals(dto.getEmail()) &&
                repository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail já em uso"
            );
        }

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setIdade(dto.getIdade());

        repository.save(usuario);
        return toDTO(usuario);
    }

    private UsuarioDTO toDTO(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setNome(u.getNome());
        dto.setEmail(u.getEmail());
        dto.setIdade(u.getIdade());
        dto.setCpf(u.getCpf());
        return dto;
    }

    public List<UsuarioDTO> listar(String status, int page, int size) throws InterruptedException {
        Pageable pageable = PageRequest.of(page, size);

        Page<Usuario> pagina;

        if (status != null && !status.isBlank()) {
            pagina = repository.findById(status, pageable);
        } else {
            pagina = repository.findAll(pageable);
        }

        return pagina.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Excluir (delete)
    public void deletar(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        repository.delete(usuario);
    }


}
