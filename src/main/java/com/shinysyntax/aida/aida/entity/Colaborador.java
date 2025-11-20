package com.shinysyntax.aida.aida.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "colaborador")
public class Colaborador {

    @Id
    @Column(name = "cpf_colaborador", length = 11)
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @Column
    private String email;

    @Column
    private String telefone;

    private LocalDate dataNascimento;

    private String cargo;
    @Convert(converter = com.shinysyntax.aida.aida.converter.ModalidadeConverter.class)
    @Column(length = 20)
    private com.shinysyntax.aida.aida.enums.Modalidade modalidade;
    private LocalDate dataAdmissao;

    @Lob
    @Column(columnDefinition = "CLOB DEFAULT 'NÃO POSSUI PROBLEMAS DE SAÚDE'")
    private String problemaSaude = "NÃO POSSUI PROBLEMAS DE SAÚDE";

    @Lob
    @Column(columnDefinition = "CLOB DEFAULT 'NÃO FAZ USO DE MEDICAMENTO CONTROLADO'")
    private String medicamentoUsoDiario = "NÃO FAZ USO DE MEDICAMENTO CONTROLADO";

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Agenda> agendas = new ArrayList<>();

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RegistroDiario> registros = new ArrayList<>();

    public Colaborador() {
        // ensure default values on entity level as well
        if (this.problemaSaude == null) this.problemaSaude = "NÃO POSSUI PROBLEMAS DE SAÚDE";
        if (this.medicamentoUsoDiario == null) this.medicamentoUsoDiario = "NÃO FAZ USO DE MEDICAMENTO CONTROLADO";
    }

    // getters and setters
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public com.shinysyntax.aida.aida.enums.Modalidade getModalidade() { return modalidade; }
    public void setModalidade(com.shinysyntax.aida.aida.enums.Modalidade modalidade) { this.modalidade = modalidade; }
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }
    public String getProblemaSaude() { return problemaSaude; }
    public void setProblemaSaude(String problemaSaude) { this.problemaSaude = problemaSaude; }
    public String getMedicamentoUsoDiario() { return medicamentoUsoDiario; }
    public void setMedicamentoUsoDiario(String medicamentoUsoDiario) { this.medicamentoUsoDiario = medicamentoUsoDiario; }
    public List<Agenda> getAgendas() { return agendas; }
    public void setAgendas(List<Agenda> agendas) { this.agendas = agendas; }
    public List<RegistroDiario> getRegistros() { return registros; }
    public void setRegistros(List<RegistroDiario> registros) { this.registros = registros; }
}
