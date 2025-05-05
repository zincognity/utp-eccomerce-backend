package com.utepinos.utp_ecommerce.api.auth.core.domain.exception;

import com.utepinos.utp_ecommerce.api.shared.domain.exception.ErrorException;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * * Excepción cuando las credenciales son inválidas.
 */
@Getter
@NoArgsConstructor
public final class InvalidCredentialsException extends ErrorException {
  private final String message = "Credenciales inválidas";
  private final int status = 401;
}
