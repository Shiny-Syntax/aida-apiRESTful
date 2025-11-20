package com.shinysyntax.aida.aida.dto.request;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AgendaRequest {

    @NotBlank(message = "O campo 'tipo' é obrigatório.")
    private String tipo;

    private String descricao;

<<<<<<< HEAD
    @NotNull(message = "dataHora é obrigatório")
    @Future(message = "dataHora deve ser uma data/hora futura")
    private LocalDateTime dataHora;
=======
    @NotNull(message = "O campo 'dataHora' é obrigatório.")
    private OffsetDateTime dataHora;
>>>>>>> b91737a398d197c9a9584e9fbd38c840654268d0

    @NotBlank(message = "O campo 'prioridade' é obrigatório e deve ser: ALTA, MEDIA ou BAIXA.")
    private String prioridade;
    private String plataforma;
    @NotBlank(message = "O campo 'status' é obrigatório e deve ser : AGENDADO, EM ANDAMENTO, CANCELADO, CONCLUÍDO")
    private String status;

<<<<<<< HEAD
    @NotBlank(message = "colaboradorCpf é obrigatório")
=======
    @NotBlank(message = "O CPF do colaborador é obrigatório.")
>>>>>>> b91737a398d197c9a9584e9fbd38c840654268d0
    private String colaboradorCpf;

    // getters and setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public OffsetDateTime getDataHora() { return dataHora; }
    public void setDataHora(OffsetDateTime dataHora) { this.dataHora = dataHora; }
    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }
    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getColaboradorCpf() { return colaboradorCpf; }
    public void setColaboradorCpf(String colaboradorCpf) { this.colaboradorCpf = colaboradorCpf; }
}
