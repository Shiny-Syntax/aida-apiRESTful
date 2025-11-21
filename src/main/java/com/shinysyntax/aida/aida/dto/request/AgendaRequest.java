package com.shinysyntax.aida.aida.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public class AgendaRequest {
    @NotBlank
    private String tipo;

    private String descricao;

    // usuário informa apenas a data de entrega prevista (somente data); dataHora será definida pelo sistema
    private LocalDate dataEntrega;

    private String prioridade;
    private String plataforma;
    private String status;

    @NotBlank(message = "colaboradorCpf is required")
    private String colaboradorCpf;

    // getters and setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDataEntrega() { return dataEntrega; }
    public void setDataEntrega(LocalDate dataEntrega) { this.dataEntrega = dataEntrega; }
    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }
    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getColaboradorCpf() { return colaboradorCpf; }
    public void setColaboradorCpf(String colaboradorCpf) { this.colaboradorCpf = colaboradorCpf; }
}
