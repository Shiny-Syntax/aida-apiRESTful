package com.shinysyntax.aida.aida.enums;

import com.shinysyntax.aida.aida.exception.BadRequestException;

public enum Priority {
    BAIXA("BAIXA"),
    MEDIA("MÉDIA"),
    ALTA("ALTA"),
    URGENTE("URGENTE");

    private final String label;

    Priority(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Priority fromLabel(String label) {
        if (label == null) return null;
        String normalized = label.trim();
        for (Priority p : values()) {
            if (p.label.equalsIgnoreCase(normalized) || p.name().equalsIgnoreCase(normalized)) {
                return p;
            }
        }
        throw new BadRequestException("Invalid priority value: " + label);
    }
}
