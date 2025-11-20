package com.shinysyntax.aida.aida.mapper;

import com.shinysyntax.aida.aida.dto.request.AgendaRequest;
import com.shinysyntax.aida.aida.dto.response.AgendaResponse;
import com.shinysyntax.aida.aida.entity.Agenda;
import com.shinysyntax.aida.aida.entity.Colaborador;

public class AgendaMapper {

    public static Agenda toEntity(AgendaRequest req, Colaborador colaborador) {
        if (req == null) return null;
        Agenda a = new Agenda();
        a.setTipo(req.getTipo());
        a.setDescricao(req.getDescricao());
        // request now supplies an OffsetDateTime (ISO/Z). Convert to LocalDateTime for the entity.
        if (req.getDataHora() != null) {
            a.setDataHora(req.getDataHora().toLocalDateTime());
        } else {
            a.setDataHora(null);
        }
        // map prioridade and status safely
        if (req.getPrioridade() != null) {
            a.setPrioridade(com.shinysyntax.aida.aida.enums.Priority.fromLabel(req.getPrioridade()));
        }
        a.setPlataforma(req.getPlataforma());
        if (req.getStatus() != null) {
            a.setStatus(com.shinysyntax.aida.aida.enums.AgendaStatus.fromLabel(req.getStatus()));
        }
        a.setColaborador(colaborador);
        return a;
    }

    public static AgendaResponse toResponse(Agenda a) {
        if (a == null) return null;
        AgendaResponse r = new AgendaResponse();
        r.setId(a.getId());
        r.setTipo(a.getTipo());
        r.setDescricao(a.getDescricao());
        r.setDataHora(a.getDataHora());
        r.setPrioridade(a.getPrioridade() == null ? null : a.getPrioridade().getLabel());
        r.setPlataforma(a.getPlataforma());
        r.setStatus(a.getStatus() == null ? null : a.getStatus().getLabel());
        if (a.getColaborador() != null) {
            r.setColaboradorCpf(a.getColaborador().getCpf() == null ? null : String.valueOf(a.getColaborador().getCpf()));
            r.setColaboradorNome(a.getColaborador().getNome());
        }
        return r;
    }
}
