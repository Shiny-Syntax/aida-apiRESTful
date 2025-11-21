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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/registros")
@Validated
public class RegistroDiarioController {

    private final RegistroDiarioService service;
    private final ColaboradorRepository colaboradorRepository;

    public RegistroDiarioController(RegistroDiarioService service, ColaboradorRepository colaboradorRepository) {
        this.service = service; this.colaboradorRepository = colaboradorRepository;
    }

    @GetMapping
    @Operation(summary = "Listar registros", description = "Retorna todos os registros diários")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public List<RegistroDiarioResponse> list() { return service.findAll().stream().map(RegistroDiarioMapper::toResponse).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    @Operation(summary = "Obter registro", description = "Retorna registro diário por id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "404", description = "Record not found"),
        @ApiResponse(responseCode = "422", description = "Validation Error"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public RegistroDiarioResponse get(@Parameter(description = "ID do registro") @PathVariable Long id) { return RegistroDiarioMapper.toResponse(service.findById(id)); }

    @PostMapping
    @Operation(summary = "Criar registro", description = "Cria um registro diário vinculado a um colaborador (use colaboradorCpf)")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "404", description = "Collaborator not found"),
        @ApiResponse(responseCode = "422", description = "Validation Error"),
        @ApiResponse(responseCode = "409", description = "Conflict - data integrity"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<RegistroDiarioResponse> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do registro") @Valid @RequestBody RegistroDiarioRequest req) {
        String cpf = Objects.requireNonNull(req.getColaboradorCpf(), "colaboradorCpf must not be null");
        Colaborador c = colaboradorRepository.findById(cpf)
            .orElseThrow(() -> new ColaboradorNotFoundException("Colaborador not found: " + cpf));
        RegistroDiario toSave = RegistroDiarioMapper.toEntity(req, c);
        // system-controlled date: always set to current date
        toSave.setDataRegistro(java.time.LocalDate.now());
        RegistroDiario saved = service.create(toSave);
        RegistroDiarioResponse resp = RegistroDiarioMapper.toResponse(Objects.requireNonNull(saved));
        URI uri = URI.create("/api/registros/" + resp.getId());
        Objects.requireNonNull(uri);
        return ResponseEntity.created(uri).body(resp);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registro", description = "Atualiza um registro diário existente (forneça colaboradorCpf)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "404", description = "Record or collaborator not found"),
        @ApiResponse(responseCode = "422", description = "Validation Error"),
        @ApiResponse(responseCode = "409", description = "Conflict - data integrity"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public RegistroDiarioResponse update(@Parameter(description = "ID do registro") @PathVariable Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados do registro") @Valid @RequestBody RegistroDiarioRequest req) {
        Objects.requireNonNull(id, "id must not be null");
        String cpf = Objects.requireNonNull(req.getColaboradorCpf(), "colaboradorCpf must not be null");
        Colaborador c = colaboradorRepository.findById(cpf)
            .orElseThrow(() -> new ColaboradorNotFoundException("Colaborador not found: " + cpf));
        RegistroDiario updated = service.update(id, RegistroDiarioMapper.toEntity(req, c));
        return RegistroDiarioMapper.toResponse(Objects.requireNonNull(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover registro", description = "Remove registro diário por id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(responseCode = "404", description = "Record not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID do registro") @PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
