package com.shinysyntax.aida.aida.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "registro_diario")
public class RegistroDiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro")
    private Long id;

    private LocalDate dataRegistro;

    private Integer escalaEmocional;
    private Integer tempoTela;
    private Integer pausasRealizadas;

    @Column(length = 400)
    private String observacoesColaborador;

    @Lob
    private String observacoesAIDA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colaborador_cpf_colaborador")
    private Colaborador colaborador;

    public RegistroDiario() {}

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public Colaborador getColaborador() { return colaborador; }
    public void setColaborador(Colaborador colaborador) { this.colaborador = colaborador; }
}
