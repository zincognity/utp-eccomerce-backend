package com.utepinos.utp_ecommerce.api.shared.domain.exception;


import lombok.Getter;

/**
 * * Error de no resultados en búsquedas.
 */
@Getter
public final class NotFoundException extends ErrorException {
    private String message = "% no encontrado(a)";
    private final int status = 404;

    public NotFoundException() {
        this.message = this.message.replace("%", "Recurso");
    }

    public NotFoundException(String entity) {
        this.message = this.message.replace("%", entity);
    }

    public NotFoundException(Class<?> entity) {
        this(entity.getName());
    }

    public NotFoundException(String entity, Object id) {
        this.message = this.message.replace("%", entity + " con id " + id);
    }

    public NotFoundException(Class<?> entity, Object id) {
        this(entity.getName(), id);
    }

    public NotFoundException(String entity, String field, Object value) {
        this.message = this.message.replace("%", entity + " con " + field + " " + value);
    }

    public NotFoundException(Class<?> entity, String field, Object value) {
        this(entity.getName(), field, value);
    }
}
