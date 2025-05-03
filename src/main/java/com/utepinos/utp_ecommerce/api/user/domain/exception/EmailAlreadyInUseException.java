package com.utepinos.utp_ecommerce.api.user.domain.exception;

import lombok.Getter;

/**
 * * Excepción para crear usuarios.
 */
@Getter
public class EmailAlreadyInUseException extends RuntimeException {
  private final String message = "El email ya se encuentra en uso";
  private final int status = 409;
}
