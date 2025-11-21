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

import com.shinysyntax.aida.aida.dto.request.ColaboradorRequest;
import com.shinysyntax.aida.aida.dto.response.ColaboradorResponse;
import com.shinysyntax.aida.aida.entity.Colaborador;
import com.shinysyntax.aida.aida.mapper.ColaboradorMapper;
import com.shinysyntax.aida.aida.service.ColaboradorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/colaboradores")
@Validated
public class ColaboradorController {

    private final ColaboradorService service;

    public ColaboradorController(ColaboradorService service) { this.service = service; }

    @GetMapping
    @Operation(summary = "Listar colaboradores", description = "Retorna todos os colaboradores cadastrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public List<ColaboradorResponse> list() {
        return service.findAll().stream().map(ColaboradorMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{cpf}")
    @Operation(summary = "Obter colaborador", description = "Retorna um colaborador por CPF")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "404", description = "Collaborator not found"),
        @ApiResponse(responseCode = "422", description = "Validation Error"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ColaboradorResponse get(@Parameter(description = "CPF do colaborador") @PathVariable String cpf) { return ColaboradorMapper.toResponse(service.findByCpf(cpf)); }

    @PostMapping
    @Operation(summary = "Criar colaborador", description = "Cria um novo colaborador")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "422", description = "Validation Error"),
        @ApiResponse(responseCode = "409", description = "Conflict - data integrity"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ColaboradorResponse> create(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do colaborador a criar") @Valid @RequestBody ColaboradorRequest req) {
        Colaborador saved = service.create(ColaboradorMapper.toEntity(req));
        ColaboradorResponse resp = ColaboradorMapper.toResponse(Objects.requireNonNull(saved));
        URI uri = URI.create("/api/colaboradores/" + resp.getCpf());
        Objects.requireNonNull(uri);
        return ResponseEntity.created(uri).body(resp);
    }

    @PutMapping("/{cpf}")
    @Operation(summary = "Atualizar colaborador", description = "Atualiza dados de um colaborador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "404", description = "Collaborator not found"),
        @ApiResponse(responseCode = "422", description = "Validation Error"),
        @ApiResponse(responseCode = "409", description = "Conflict - data integrity"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ColaboradorResponse update(@Parameter(description = "CPF do colaborador") @PathVariable String cpf, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados do colaborador") @Valid @RequestBody ColaboradorRequest req) {
        Colaborador updated = service.update(cpf, ColaboradorMapper.toEntity(req));
        return ColaboradorMapper.toResponse(updated);
    }

    @DeleteMapping("/{cpf}")
    @Operation(summary = "Remover colaborador", description = "Remove um colaborador pelo CPF")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(responseCode = "404", description = "Collaborator not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "CPF do colaborador") @PathVariable String cpf) {
        service.delete(cpf);
        return ResponseEntity.noContent().build();
    }
}
