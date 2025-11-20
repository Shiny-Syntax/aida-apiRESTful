package com.shinysyntax.aida.aida.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RegistroDiarioResponse {
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataRegistro;
    private Double escalaEmocional;
    private Integer tempoTela;
    private Integer pausasRealizadas;
    private String observacoesColaborador;
    private String observacoesAIDA;
    private String colaboradorCpf;
    private String colaboradorNome;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDate dataRegistro) { this.dataRegistro = dataRegistro; }
    public Double getEscalaEmocional() { return escalaEmocional; }
    public void setEscalaEmocional(Double escalaEmocional) { this.escalaEmocional = escalaEmocional; }
    public Integer getTempoTela() { return tempoTela; }
    public void setTempoTela(Integer tempoTela) { this.tempoTela = tempoTela; }
    public Integer getPausasRealizadas() { return pausasRealizadas; }
    public void setPausasRealizadas(Integer pausasRealizadas) { this.pausasRealizadas = pausasRealizadas; }
    public String getObservacoesColaborador() { return observacoesColaborador; }
    public void setObservacoesColaborador(String observacoesColaborador) { this.observacoesColaborador = observacoesColaborador; }
    public String getObservacoesAIDA() { return observacoesAIDA; }
    public void setObservacoesAIDA(String observacoesAIDA) { this.observacoesAIDA = observacoesAIDA; }
    public String getColaboradorCpf() { return colaboradorCpf; }
    public void setColaboradorCpf(String colaboradorCpf) { this.colaboradorCpf = colaboradorCpf; }
    public String getColaboradorNome() { return colaboradorNome; }
    public void setColaboradorNome(String colaboradorNome) { this.colaboradorNome = colaboradorNome; }
}
