package com.shinysyntax.aida.aida.entity;

import java.time.LocalDateTime;

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
@Table(name = "agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atividade")
    private Long id;

    private String tipo;

    @Lob
    private String descricao;

    private LocalDateTime dataHora;

    @jakarta.persistence.Convert(converter = com.shinysyntax.aida.aida.converter.PriorityConverter.class)
    @Column(length = 20)
    private com.shinysyntax.aida.aida.enums.Priority prioridade;
    private String plataforma;
    @jakarta.persistence.Convert(converter = com.shinysyntax.aida.aida.converter.AgendaStatusConverter.class)
    @Column(length = 20)
    private com.shinysyntax.aida.aida.enums.AgendaStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colaborador_cpf_colaborador")
    private Colaborador colaborador;

    public Agenda() {}

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public com.shinysyntax.aida.aida.enums.Priority getPrioridade() { return prioridade; }
    public void setPrioridade(com.shinysyntax.aida.aida.enums.Priority prioridade) { this.prioridade = prioridade; }
    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public com.shinysyntax.aida.aida.enums.AgendaStatus getStatus() { return status; }
    public void setStatus(com.shinysyntax.aida.aida.enums.AgendaStatus status) { this.status = status; }
    public Colaborador getColaborador() { return colaborador; }
    public void setColaborador(Colaborador colaborador) { this.colaborador = colaborador; }
}
