package com.utepinos.utp_ecommerce.api.auth.core.domain.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * * Excepción cuando las credenciales son inválidas.
 */
@Getter
@NoArgsConstructor
public final class InvalidCredentialsException extends RuntimeException {
  private final String message = "Credenciales inválidas";
  private final int status = 401;
}
