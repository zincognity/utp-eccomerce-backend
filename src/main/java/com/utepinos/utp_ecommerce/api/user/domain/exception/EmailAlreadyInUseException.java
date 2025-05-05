package com.utepinos.utp_ecommerce.api.user.domain.exception;

import com.utepinos.utp_ecommerce.api.shared.domain.exception.ErrorException;

import lombok.Getter;

/**
 * * Excepción para crear usuarios.
 */
@Getter
public class EmailAlreadyInUseException extends ErrorException {
  private final String message = "El email ya se encuentra en uso";
  private final int status = 409;
}
