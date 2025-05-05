package com.utepinos.utp_ecommerce.api.shared.domain.exception;


import lombok.Getter;

/**
 * * Excepción de error interno en el servidor.
 */
@Getter
public final class InternalServerErrorException extends ErrorException {
    private final String message = "Internal server error";
    private final int status = 500;
    private final Throwable cause;

    public InternalServerErrorException(Throwable cause) {
        this.cause = cause;
    }
}
