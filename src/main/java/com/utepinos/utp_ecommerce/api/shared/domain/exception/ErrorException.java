package com.utepinos.utp_ecommerce.api.shared.domain.exception;

/**
 * * Excepción global o generalizada.
 */
public abstract class ErrorException extends RuntimeException {
    public abstract int getStatus();
}
