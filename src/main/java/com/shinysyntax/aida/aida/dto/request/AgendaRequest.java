package com.shinysyntax.aida.aida.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AgendaRequest {
    private Long id;

    @NotBlank
    private String tipo;

    private String descricao;

    @NotNull(message = "dataHora é obrigatório")
    @Future(message = "dataHora deve ser uma data/hora futura")
    private LocalDateTime dataHora;

    private String prioridade;
    private String plataforma;
    private String status;

    @NotBlank(message = "colaboradorCpf é obrigatório")
    private String colaboradorCpf;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }
    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getColaboradorCpf() { return colaboradorCpf; }
    public void setColaboradorCpf(String colaboradorCpf) { this.colaboradorCpf = colaboradorCpf; }
}
