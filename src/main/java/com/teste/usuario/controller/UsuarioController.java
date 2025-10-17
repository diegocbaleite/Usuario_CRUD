package com.teste.usuario.controller;

import com.teste.usuario.dto.UsuarioDTO;
import com.teste.usuario.service.UsuarioService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // CREATE metodo POST http://localhost:8080/api/usuarios
    @PostMapping
    public ResponseEntity<Map<String, Object>> criar(@Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO novo = usuarioService.criar(dto);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Usuário cadastrado com sucesso!");
        resposta.put("usuario", novo);

        System.out.println("Novo usuário cadastrado: " + novo.getNome() + " (ID: " + novo.getId() + ")");

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }


    // READ (by ID) metodo GET http://localhost:8080/api/usuarios/1
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
        return usuarioService.buscar(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"
                ));
    }

    // LIST metodo GET http://localhost:8080/api/usuarios
    @SneakyThrows
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<UsuarioDTO> usuarios = usuarioService.listar(status, page, size);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO dto
    ) {
        UsuarioDTO atualizado = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Usuário excluído com sucesso!");
        resposta.put("status", 200);

        System.out.println("Usuário com ID " + id + " foi excluído.");

        return ResponseEntity.ok(resposta);
    }

}