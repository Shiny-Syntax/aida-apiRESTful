package com.shinysyntax.aida.aida.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.shinysyntax.aida.aida.entity.RegistroDiario;
import com.shinysyntax.aida.aida.exception.ResourceNotFoundException;
import com.shinysyntax.aida.aida.repository.RegistroDiarioRepository;
import com.shinysyntax.aida.aida.service.RegistroDiarioService;

@Service
public class RegistroDiarioServiceImpl implements RegistroDiarioService {

    private final RegistroDiarioRepository repo;

    public RegistroDiarioServiceImpl(RegistroDiarioRepository repo) { this.repo = repo; }

    @Override
    public RegistroDiario create(RegistroDiario registro) { Objects.requireNonNull(registro, "registro must not be null"); return Objects.requireNonNull(repo.save(registro)); }

    @Override
    public RegistroDiario update(Long id, RegistroDiario registro) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(registro, "registro must not be null");
        RegistroDiario existing = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registro not found"));
        existing.setEscalaEmocional(registro.getEscalaEmocional());
        existing.setTempoTela(registro.getTempoTela());
        existing.setPausasRealizadas(registro.getPausasRealizadas());
        existing.setObservacoesColaborador(registro.getObservacoesColaborador());
        existing.setObservacoesAIDA(registro.getObservacoesAIDA());
        return Objects.requireNonNull(repo.save(existing));
    }

    @Override
    public RegistroDiario findById(Long id) { Objects.requireNonNull(id, "id must not be null"); return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registro not found")); }

    @Override
    public List<RegistroDiario> findAll() { return repo.findAll(); }

    @Override
    public void delete(Long id) { Objects.requireNonNull(id, "id must not be null"); repo.deleteById(id); }
}
