package com.shinysyntax.aida.aida.mapper;

import com.shinysyntax.aida.aida.dto.request.RegistroDiarioRequest;
import com.shinysyntax.aida.aida.dto.response.RegistroDiarioResponse;
import com.shinysyntax.aida.aida.entity.Colaborador;
import com.shinysyntax.aida.aida.entity.RegistroDiario;

public class RegistroDiarioMapper {

    /**
     * Mapeia o DTO para Entity (SEM SETAR ID!)
     * O ID é controlado 100% pelo Hibernate.
     */
    public static RegistroDiario toEntity(RegistroDiarioRequest req, Colaborador colaborador) {
        if (req == null) return null;

        RegistroDiario r = new RegistroDiario();

        r.setDataRegistro(req.getDataRegistro());
        r.setEscalaEmocional(req.getEscalaEmocional() == null ? null : req.getEscalaEmocional().doubleValue());
        r.setTempoTela(req.getTempoTela());
        r.setPausasRealizadas(req.getPausasRealizadas());
        r.setObservacoesColaborador(req.getObservacoesColaborador());
        r.setObservacoesAIDA(req.getObservacoesAIDA());
        r.setColaborador(colaborador);

        return r;
    }

    /**
     * Entity → DTO de resposta
     */
    public static RegistroDiarioResponse toResponse(RegistroDiario r) {
        if (r == null) return null;

        RegistroDiarioResponse resp = new RegistroDiarioResponse();

        resp.setId(r.getId());
        resp.setDataRegistro(r.getDataRegistro());
        resp.setEscalaEmocional(r.getEscalaEmocional());
        resp.setTempoTela(r.getTempoTela());
        resp.setPausasRealizadas(r.getPausasRealizadas());
        resp.setObservacoesColaborador(r.getObservacoesColaborador());
        resp.setObservacoesAIDA(r.getObservacoesAIDA());

        if (r.getColaborador() != null) {
            resp.setColaboradorCpf(r.getColaborador().getCpf() == null ? null : String.valueOf(r.getColaborador().getCpf()));
            resp.setColaboradorNome(r.getColaborador().getNome());
        }

        return resp;
    }
}
