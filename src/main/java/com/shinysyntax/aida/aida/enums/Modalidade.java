package com.shinysyntax.aida.aida.enums;

import com.shinysyntax.aida.aida.exception.BadRequestException;

public enum Modalidade {
    PRESENCIAL("PRESENCIAL"),
    HIBRIDO("HÍBRIDO"),
    REMOTO("REMOTO");

    private final String label;

    Modalidade(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Modalidade fromLabel(String label) {
        if (label == null) return null;
        String normalized = label.trim();
        for (Modalidade m : values()) {
            if (m.label.equalsIgnoreCase(normalized) || m.name().equalsIgnoreCase(normalized)) {
                return m;
            }
        }
        throw new BadRequestException("Invalid modality value: " + label);
    }
}
