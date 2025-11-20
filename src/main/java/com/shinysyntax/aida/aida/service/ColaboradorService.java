package com.shinysyntax.aida.aida.service;

import java.util.List;

import com.shinysyntax.aida.aida.entity.Colaborador;

public interface ColaboradorService {
    Colaborador create(Colaborador colaborador);
    Colaborador update(Long cpf, Colaborador colaborador);
    Colaborador findByCpf(Long cpf);
    List<Colaborador> findAll();
    void delete(Long cpf);
}
