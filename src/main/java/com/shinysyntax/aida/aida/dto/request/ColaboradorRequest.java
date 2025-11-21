package com.shinysyntax.aida.aida.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ColaboradorRequest {

    @NotBlank(message = "CPF is required")
    @Size(max = 11, message = "CPF must have at most 11 characters")
    private String cpf;

    @NotBlank(message = "Name is required")
    private String nome;

    @Email(message = "Invalid email")
    private String email;

    private String telefone;
    private LocalDate dataNascimento;
    private String cargo;
    private String modalidade;
    private LocalDate dataAdmissao;
    private String problemaSaude;
    private String medicamentoUsoDiario;

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
    public String getModalidade() { return modalidade; }
    public void setModalidade(String modalidade) { this.modalidade = modalidade; }
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }
    public String getProblemaSaude() { return problemaSaude; }
    public void setProblemaSaude(String problemaSaude) { this.problemaSaude = problemaSaude; }
    public String getMedicamentoUsoDiario() { return medicamentoUsoDiario; }
    public void setMedicamentoUsoDiario(String medicamentoUsoDiario) { this.medicamentoUsoDiario = medicamentoUsoDiario; }
}
