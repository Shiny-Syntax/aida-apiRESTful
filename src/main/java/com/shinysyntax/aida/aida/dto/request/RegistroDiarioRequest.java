package com.shinysyntax.aida.aida.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegistroDiarioRequest {
    

    @NotNull(message = "O campo 'dataRegistro' é obrigatório.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataRegistro;

    @Min(value = 0, message = "A escala emocional deve ser entre 0 e 10.")
    @Max(value = 10, message = "A escala emocional deve ser entre 0 e 10.")
    private Integer escalaEmocional;

    private Integer tempoTela;
    private Integer pausasRealizadas;
    private String observacoesColaborador;
    private String observacoesAIDA;

    @NotBlank(message = "O CPF do colaborador é obrigatório.")
    private String colaboradorCpf;

    // getters and setters

    public LocalDate getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDate dataRegistro) { this.dataRegistro = dataRegistro; }
    public Integer getEscalaEmocional() { return escalaEmocional; }
    public void setEscalaEmocional(Integer escalaEmocional) { this.escalaEmocional = escalaEmocional; }
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
}
