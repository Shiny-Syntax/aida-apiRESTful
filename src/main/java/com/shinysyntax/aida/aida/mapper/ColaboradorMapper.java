package com.shinysyntax.aida.aida.mapper;

import com.shinysyntax.aida.aida.dto.request.ColaboradorRequest;
import com.shinysyntax.aida.aida.dto.response.ColaboradorResponse;
import com.shinysyntax.aida.aida.entity.Colaborador;
import com.shinysyntax.aida.aida.enums.Modalidade;
import com.shinysyntax.aida.aida.exception.BadRequestException;

public class ColaboradorMapper {

    public static Colaborador toEntity(ColaboradorRequest req) {
        if (req == null) return null;
        Colaborador c = new Colaborador();
        // CPF in DTO is a string; DB stores it as BIGINT
        if (req.getCpf() != null) {
            try {
                c.setCpf(Long.valueOf(req.getCpf()));
            } catch (NumberFormatException ex) {
                throw new com.shinysyntax.aida.aida.exception.BadRequestException("cpf must be numeric");
            }
        }
        c.setNome(req.getNome());
        c.setEmail(req.getEmail());
        c.setTelefone(req.getTelefone());
        c.setDataNascimento(req.getDataNascimento());
        c.setCargo(req.getCargo());
        try {
            c.setModalidade(Modalidade.fromLabel(req.getModalidade()));
        } catch (BadRequestException e) {
            throw e;
        }
        c.setDataAdmissao(req.getDataAdmissao());
        c.setProblemaSaude(req.getProblemaSaude());
        c.setMedicamentoUsoDiario(req.getMedicamentoUsoDiario());
        return c;
    }

    public static ColaboradorResponse toResponse(Colaborador c) {
        if (c == null) return null;
        ColaboradorResponse r = new ColaboradorResponse();
        r.setCpf(c.getCpf() == null ? null : String.valueOf(c.getCpf()));
        r.setNome(c.getNome());
        r.setEmail(c.getEmail());
        r.setTelefone(c.getTelefone());
        r.setDataNascimento(c.getDataNascimento());
        r.setCargo(c.getCargo());
        r.setModalidade(c.getModalidade() == null ? null : c.getModalidade().getLabel());
        r.setDataAdmissao(c.getDataAdmissao());
        r.setProblemaSaude(c.getProblemaSaude());
        r.setMedicamentoUsoDiario(c.getMedicamentoUsoDiario());
        return r;
    }
}
