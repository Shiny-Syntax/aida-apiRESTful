package com.shinysyntax.aida.aida.service;

import java.util.List;

import com.shinysyntax.aida.aida.entity.Colaborador;

public interface ColaboradorService {
    Colaborador create(Colaborador colaborador);
    Colaborador update(String cpf, Colaborador colaborador);
    Colaborador findByCpf(String cpf);
    List<Colaborador> findAll();
    void delete(String cpf);
}
