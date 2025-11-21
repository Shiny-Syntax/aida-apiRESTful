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

import com.shinysyntax.aida.aida.dto.request.AgendaRequest;
import com.shinysyntax.aida.aida.dto.response.AgendaResponse;
import com.shinysyntax.aida.aida.entity.Agenda;
import com.shinysyntax.aida.aida.entity.Colaborador;
import com.shinysyntax.aida.aida.exception.ColaboradorNotFoundException;
import com.shinysyntax.aida.aida.mapper.AgendaMapper;
import com.shinysyntax.aida.aida.repository.ColaboradorRepository;
import com.shinysyntax.aida.aida.service.AgendaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agenda")
@Validated
public class AgendaController {

    private final AgendaService service;
    private final ColaboradorRepository colaboradorRepository;

    public AgendaController(AgendaService service, ColaboradorRepository colaboradorRepository) {
        this.service = service; this.colaboradorRepository = colaboradorRepository;
    }

    @GetMapping
    @Operation(summary = "Listar agendas", description = "Retorna todas agendas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public List<AgendaResponse> list() { return service.findAll().stream().map(AgendaMapper::toResponse).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    @Operation(summary = "Obter agenda", description = "Retorna agenda por id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "404", description = "Agenda not found"),
        @ApiResponse(responseCode = "422", description = "Validation Error"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public AgendaResponse get(@Parameter(description = "ID da agenda") @PathVariable Long id) { return AgendaMapper.toResponse(service.findById(id)); }

    @PostMapping
    @Operation(summary = "Criar agenda", description = "Cria uma nova agenda vinculada a um colaborador (use colaboradorCpf)")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "404", description = "Collaborator not found"),
        @ApiResponse(responseCode = "422", description = "Validation Error"),
        @ApiResponse(responseCode = "409", description = "Conflict - data integrity"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<AgendaResponse> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da agenda") @Valid @RequestBody AgendaRequest req) {
        String cpf = Objects.requireNonNull(req.getColaboradorCpf(), "colaboradorCpf must not be null");
        Colaborador c = colaboradorRepository.findById(cpf)
            .orElseThrow(() -> new ColaboradorNotFoundException("Colaborador not found: " + cpf));
        Agenda saved = service.create(AgendaMapper.toEntity(req, c));
        AgendaResponse resp = AgendaMapper.toResponse(Objects.requireNonNull(saved));
        URI uri = URI.create("/api/agenda/" + resp.getId());
        Objects.requireNonNull(uri);
        return ResponseEntity.created(uri).body(resp);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar agenda", description = "Atualiza uma agenda existente (forneça colaboradorCpf)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "404", description = "Agenda or collaborator not found"),
        @ApiResponse(responseCode = "422", description = "Validation Error"),
        @ApiResponse(responseCode = "409", description = "Conflict - data integrity"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public AgendaResponse update(@Parameter(description = "ID da agenda") @PathVariable Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados da agenda") @Valid @RequestBody AgendaRequest req) {
        Objects.requireNonNull(id, "id must not be null");
        String cpf = Objects.requireNonNull(req.getColaboradorCpf(), "colaboradorCpf must not be null");
        Colaborador c = colaboradorRepository.findById(cpf)
            .orElseThrow(() -> new ColaboradorNotFoundException("Colaborador not found: " + cpf));
        Agenda updated = service.update(id, AgendaMapper.toEntity(req, c));
        return AgendaMapper.toResponse(Objects.requireNonNull(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover agenda", description = "Remove agenda por id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(responseCode = "404", description = "Agenda not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID da agenda") @PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
