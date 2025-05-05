package com.utepinos.utp_ecommerce.api.shared.domain.exception;


import lombok.Getter;

/**
 * * Excepción para no reducir la cantidad de un producto en negativo.
 */
@Getter
public final class CannotReduceProductQuantityexception extends ErrorException {
    private final String message = "No se puede reducir a negativo la cantidad de un producto";
    private final int status = 409;
}
