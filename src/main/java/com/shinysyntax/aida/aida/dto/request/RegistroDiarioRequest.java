package com.shinysyntax.aida.aida.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RegistroDiarioRequest {
    

    // dataRegistro é controlada pelo sistema (definida no momento da criação)

    @Min(value = 0, message = "escalaEmocional must be at least 0")
    @Max(value = 9, message = "escalaEmocional must be at most 9")
    private Integer escalaEmocional;

    private Integer tempoTela;
    private Integer pausasRealizadas;
    private String observacoesColaborador;
    private String observacoesAIDA;

    @NotBlank(message = "colaboradorCpf is required")
    private String colaboradorCpf;

    // getters and setters
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
