package com.shinysyntax.aida.aida.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.shinysyntax.aida.aida.entity.Agenda;
import com.shinysyntax.aida.aida.exception.ResourceNotFoundException;
import com.shinysyntax.aida.aida.repository.AgendaRepository;
import com.shinysyntax.aida.aida.service.AgendaService;

@Service
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository repo;

    public AgendaServiceImpl(AgendaRepository repo) { this.repo = repo; }

    @Override
    public Agenda create(Agenda agenda) {
        Objects.requireNonNull(agenda, "agenda must not be null");
        // set allocation timestamp (system-controlled)
        agenda.setDataHora(java.time.LocalDateTime.now());
        // business validation: dataEntrega (if provided) should be a future date
        if (agenda.getDataEntrega() != null && !agenda.getDataEntrega().isAfter(java.time.LocalDate.now())) {
            throw new com.shinysyntax.aida.aida.exception.BadRequestException("dataEntrega must be in the future (date only)");
        }
        return Objects.requireNonNull(repo.save(agenda));
    }

    @Override
    public Agenda update(Long id, Agenda agenda) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(agenda, "agenda must not be null");
        Agenda existing = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agenda not found"));
        existing.setTipo(agenda.getTipo());
        existing.setDescricao(agenda.getDescricao());
        // dataHora é controlada pelo sistema e não deve ser sobrescrita pelo usuário
        // existing.setDataHora(agenda.getDataHora());
        existing.setPrioridade(agenda.getPrioridade());
        existing.setPlataforma(agenda.getPlataforma());
        existing.setStatus(agenda.getStatus());
        existing.setDataEntrega(agenda.getDataEntrega());
        return Objects.requireNonNull(repo.save(existing));
    }

    @Override
    public Agenda findById(Long id) { Objects.requireNonNull(id, "id must not be null"); return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agenda not found")); }

    @Override
    public List<Agenda> findAll() { return repo.findAll(); }

    @Override
    public void delete(Long id) { Objects.requireNonNull(id, "id must not be null"); repo.deleteById(id); }
}
