package com.utepinos.utp_ecommerce.api.shared.domain.exception;


import lombok.Getter;

/**
 * * Excepción cuando no se puede eliminar una referencia.
 */
@Getter
public final class CannotDeleteReferencedEntity extends ErrorException {
    private String message = "No se puede eliminar el/la % porque está en uso en otros registros";
    private final int status = 409;

    public CannotDeleteReferencedEntity(Class<?> clazz) {
        this.message = this.message.replace("%", clazz.getName().substring(0, 1).toLowerCase() + clazz.getName().substring(1));
    }
}
