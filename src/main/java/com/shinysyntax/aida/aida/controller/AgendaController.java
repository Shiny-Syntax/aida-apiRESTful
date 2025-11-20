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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agenda")
@Validated
@Tag(name = "Agenda", description = "Operações relacionadas à agenda")
public class AgendaController {

    private final AgendaService service;
    private final ColaboradorRepository colaboradorRepository;

    public AgendaController(AgendaService service, ColaboradorRepository colaboradorRepository) {
        this.service = service; this.colaboradorRepository = colaboradorRepository;
    }

    @Operation(summary = "Listar agenda", description = "Retorna todas as atividades da agenda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public List<AgendaResponse> list() { return service.findAll().stream().map(AgendaMapper::toResponse).collect(Collectors.toList()); }

    @Operation(summary = "Obter atividade da agenda", description = "Retorna uma atividade da agenda pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Atividade encontrada"),
        @ApiResponse(responseCode = "404", description = "Atividade não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{id}")
    public AgendaResponse get(@PathVariable Long id) { return AgendaMapper.toResponse(service.findById(id)); }

    @Operation(summary = "Criar atividade na agenda", description = "Cria uma nova atividade vinculada a um colaborador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Atividade criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos — algum campo obrigatório está nulo"),
        @ApiResponse(responseCode = "422", description = "Erro de validação — dados do request inválidos"),
        @ApiResponse(responseCode = "404", description = "Colaborador não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<AgendaResponse> create(@Valid @RequestBody AgendaRequest req) {
        String cpf = Objects.requireNonNull(req.getColaboradorCpf(), "colaboradorCpf must not be null");
        long cpfLong;
        try { cpfLong = Long.parseLong(cpf); } catch (NumberFormatException ex) { throw new ColaboradorNotFoundException("Colaborador not found: " + cpf); }
        Colaborador c = colaboradorRepository.findById(cpfLong)
            .orElseThrow(() -> new ColaboradorNotFoundException("Colaborador not found: " + cpf));
        Agenda saved = service.create(AgendaMapper.toEntity(req, c));
        AgendaResponse resp = AgendaMapper.toResponse(Objects.requireNonNull(saved));
        URI uri = URI.create("/api/agenda/" + resp.getId());
        Objects.requireNonNull(uri);
        return ResponseEntity.created(uri).body(resp);
    }

    @Operation(summary = "Atualizar atividade da agenda", description = "Atualiza uma atividade existente da agenda")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Atividade atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "422", description = "Erro de validação — dados do request inválidos"),
        @ApiResponse(responseCode = "404", description = "Colaborador ou atividade não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{id}")
    public AgendaResponse update(@PathVariable Long id, @Valid @RequestBody AgendaRequest req) {
        Objects.requireNonNull(id, "id must not be null");
        String cpf = Objects.requireNonNull(req.getColaboradorCpf(), "colaboradorCpf must not be null");
        long cpfLong;
        try { cpfLong = Long.parseLong(cpf); } catch (NumberFormatException ex) { throw new ColaboradorNotFoundException("Colaborador not found: " + cpf); }
        Colaborador c = colaboradorRepository.findById(cpfLong)
            .orElseThrow(() -> new ColaboradorNotFoundException("Colaborador not found: " + cpf));
        Agenda updated = service.update(id, AgendaMapper.toEntity(req, c));
        return AgendaMapper.toResponse(Objects.requireNonNull(updated));
    }

    @Operation(summary = "Remover atividade da agenda", description = "Remove uma atividade da agenda pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Atividade removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Atividade não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
