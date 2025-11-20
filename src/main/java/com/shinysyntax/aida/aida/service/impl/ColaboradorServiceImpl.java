package com.shinysyntax.aida.aida.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.shinysyntax.aida.aida.entity.Colaborador;
import com.shinysyntax.aida.aida.exception.ColaboradorNotFoundException;
import com.shinysyntax.aida.aida.repository.ColaboradorRepository;
import com.shinysyntax.aida.aida.service.ColaboradorService;

@Service
public class ColaboradorServiceImpl implements ColaboradorService {

    private final ColaboradorRepository repo;

    public ColaboradorServiceImpl(ColaboradorRepository repo) { this.repo = repo; }

    @Override
    public Colaborador create(Colaborador colaborador) {
        Objects.requireNonNull(colaborador, "colaborador must not be null");
        return Objects.requireNonNull(repo.save(colaborador));
    }

    @Override
    public Colaborador update(Long cpf, Colaborador colaborador) {
        Objects.requireNonNull(cpf, "cpf must not be null");
        Objects.requireNonNull(colaborador, "colaborador must not be null");
        Colaborador existing = repo.findById(cpf).orElseThrow(() -> new ColaboradorNotFoundException("Colaborador not found: " + cpf));
        existing.setNome(colaborador.getNome());
        existing.setEmail(colaborador.getEmail());
        existing.setTelefone(colaborador.getTelefone());
        existing.setDataNascimento(colaborador.getDataNascimento());
        existing.setCargo(colaborador.getCargo());
        existing.setModalidade(colaborador.getModalidade());
        existing.setDataAdmissao(colaborador.getDataAdmissao());
        existing.setProblemaSaude(colaborador.getProblemaSaude());
        existing.setMedicamentoUsoDiario(colaborador.getMedicamentoUsoDiario());
        return Objects.requireNonNull(repo.save(existing));
    }

    @Override
    public Colaborador findByCpf(Long cpf) {
        Objects.requireNonNull(cpf, "cpf must not be null");
        return repo.findById(cpf).orElseThrow(() -> new ColaboradorNotFoundException("Colaborador not found: " + cpf));
    }

    @Override
    public List<Colaborador> findAll() { return repo.findAll(); }

    @Override
    public void delete(Long cpf) { Objects.requireNonNull(cpf, "cpf must not be null"); repo.deleteById(cpf); }

}
