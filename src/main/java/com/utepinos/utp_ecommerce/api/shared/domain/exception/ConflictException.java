package com.utepinos.utp_ecommerce.api.shared.domain.exception;

import lombok.Getter;

/**
 * * Excepción de un valor único.
 */
@Getter
public final class ConflictException extends ErrorException {
    private String message = "% debe ser único(a)";
    private final int status = 409;

    public ConflictException() {
        this.message = this.message.replace("%", "El recurso");
    }

    public ConflictException(String message) {
        this.message = message;
    }

    public ConflictException(Class<?> entity, String field) {
        String entityName = entity.getSimpleName();
        entityName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);

        this.message = this.message.replace("%", field + " de " + entityName);
    }
}
