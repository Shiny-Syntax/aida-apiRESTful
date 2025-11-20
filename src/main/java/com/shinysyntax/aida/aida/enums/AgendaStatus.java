package com.shinysyntax.aida.aida.enums;

import com.shinysyntax.aida.aida.exception.BadRequestException;

public enum AgendaStatus {
    AGENDADO("AGENDADO"),
    CANCELADO("CANCELADO"),
    CONCLUIDO("CONCLUIDO"),
    EM_ANDAMENTO("EM ANDAMENTO");

    private final String label;

    AgendaStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static AgendaStatus fromLabel(String label) {
        if (label == null) return null;
        String normalized = label.trim();
        for (AgendaStatus s : values()) {
            if (s.label.equalsIgnoreCase(normalized) || s.name().equalsIgnoreCase(normalized) || s.name().replace('_',' ').equalsIgnoreCase(normalized)) {
                return s;
            }
        }
        throw new BadRequestException("Valor de status inválido: " + label);
    }
}
