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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/colaboradores")
@Validated
@Tag(name = "Colaboradores", description = "Operações relacionadas a colaboradores")
public class ColaboradorController {

    private final ColaboradorService service;

    public ColaboradorController(ColaboradorService service) { this.service = service; }

    @Operation(summary = "Listar colaboradores", description = "Retorna a lista de todos os colaboradores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public List<ColaboradorResponse> list() {
        return service.findAll().stream().map(ColaboradorMapper::toResponse).collect(Collectors.toList());
    }

    @Operation(summary = "Obter colaborador", description = "Retorna os dados de um colaborador pelo CPF")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Colaborador encontrado"),
        @ApiResponse(responseCode = "404", description = "Colaborador não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{cpf}")
    public ColaboradorResponse get(@PathVariable Long cpf) { return ColaboradorMapper.toResponse(service.findByCpf(cpf)); }

    @Operation(summary = "Criar um novo colaborador", description = "Cria um colaborador no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Colaborador criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos — algum campo obrigatório está nulo"),
        @ApiResponse(responseCode = "422", description = "Validation Error — campos inválidos"),
        @ApiResponse(responseCode = "409", description = "Conflito — já existe um registro com este CPF"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<ColaboradorResponse> create(@Valid @RequestBody ColaboradorRequest req) {
        Colaborador saved = service.create(ColaboradorMapper.toEntity(req));
        ColaboradorResponse resp = ColaboradorMapper.toResponse(Objects.requireNonNull(saved));
        URI uri = URI.create("/api/colaboradores/" + resp.getCpf());
        Objects.requireNonNull(uri);
        return ResponseEntity.created(uri).body(resp);
    }

    @Operation(summary = "Atualizar colaborador", description = "Atualiza os dados de um colaborador existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Colaborador atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "422", description = "Validation Error — campos inválidos"),
        @ApiResponse(responseCode = "404", description = "Colaborador não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{cpf}")
    public ColaboradorResponse update(@PathVariable Long cpf, @Valid @RequestBody ColaboradorRequest req) {
        Colaborador updated = service.update(cpf, ColaboradorMapper.toEntity(req));
        return ColaboradorMapper.toResponse(updated);
    }

    @Operation(summary = "Remover colaborador", description = "Remove um colaborador pelo CPF")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Colaborador removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Colaborador não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable Long cpf) {
        service.delete(cpf);
        return ResponseEntity.noContent().build();
    }
}
