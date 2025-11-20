package com.shinysyntax.aida.aida.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinysyntax.aida.aida.dto.request.RegistroDiarioRequest;
import com.shinysyntax.aida.aida.dto.response.RegistroDiarioResponse;
import com.shinysyntax.aida.aida.entity.Colaborador;
import com.shinysyntax.aida.aida.entity.RegistroDiario;
import com.shinysyntax.aida.aida.exception.ColaboradorNotFoundException;
import com.shinysyntax.aida.aida.mapper.RegistroDiarioMapper;
import com.shinysyntax.aida.aida.repository.ColaboradorRepository;
import com.shinysyntax.aida.aida.service.RegistroDiarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/registros")
@Validated
@Tag(name = "RegistrosDiarios", description = "Operações relacionadas aos registros diários")
public class RegistroDiarioController {

    private final RegistroDiarioService service;
    private final ColaboradorRepository colaboradorRepository;

    public RegistroDiarioController(RegistroDiarioService service, ColaboradorRepository colaboradorRepository) {
        this.service = service; this.colaboradorRepository = colaboradorRepository;
    }

    @Operation(summary = "Listar registros", description = "Retorna todos os registros diários")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public List<RegistroDiarioResponse> list() { return service.findAll().stream().map(RegistroDiarioMapper::toResponse).collect(Collectors.toList()); }

    @Operation(summary = "Obter registro", description = "Retorna um registro diário pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado"),
        @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{id}")
    public RegistroDiarioResponse get(@PathVariable Long id) { return RegistroDiarioMapper.toResponse(service.findById(id)); }

    @Operation(summary = "Criar registro diário", description = "Cria um novo registro diário vinculado a um colaborador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos — algum campo obrigatório está nulo"),
        @ApiResponse(responseCode = "422", description = "Erro de validação — dados do request inválidos"),
        @ApiResponse(responseCode = "404", description = "Colaborador não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<RegistroDiarioResponse> create(@Valid @RequestBody RegistroDiarioRequest req) {
        String cpf = Objects.requireNonNull(req.getColaboradorCpf(), "colaboradorCpf must not be null");
        long cpfLong;
        try { cpfLong = Long.parseLong(cpf); } catch (NumberFormatException ex) { throw new ColaboradorNotFoundException("Colaborador not found: " + cpf); }
        Colaborador c = colaboradorRepository.findById(cpfLong)
            .orElseThrow(() -> new ColaboradorNotFoundException("Colaborador not found: " + cpf));
        RegistroDiario saved = service.create(RegistroDiarioMapper.toEntity(req, c));
        RegistroDiarioResponse resp = RegistroDiarioMapper.toResponse(Objects.requireNonNull(saved));
        URI uri = URI.create("/api/registros/" + resp.getId());
        Objects.requireNonNull(uri);
        return ResponseEntity.created(uri).body(resp);
    }

    @Operation(summary = "Atualizar registro diário", description = "Atualiza um registro diário existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "422", description = "Erro de validação — dados do request inválidos"),
        @ApiResponse(responseCode = "404", description = "Colaborador ou registro não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{id}")
    public RegistroDiarioResponse update(@PathVariable Long id, @Valid @RequestBody RegistroDiarioRequest req) {
        Objects.requireNonNull(id, "id must not be null");
        String cpf = Objects.requireNonNull(req.getColaboradorCpf(), "colaboradorCpf must not be null");
        long cpfLong;
        try { cpfLong = Long.parseLong(cpf); } catch (NumberFormatException ex) { throw new ColaboradorNotFoundException("Colaborador not found: " + cpf); }
        Colaborador c = colaboradorRepository.findById(cpfLong)
            .orElseThrow(() -> new ColaboradorNotFoundException("Colaborador not found: " + cpf));
        RegistroDiario updated = service.update(id, RegistroDiarioMapper.toEntity(req, c));
        return RegistroDiarioMapper.toResponse(Objects.requireNonNull(updated));
    }

    @Operation(summary = "Remover registro diário", description = "Remove um registro diário pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Registro removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
