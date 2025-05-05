package com.utepinos.utp_ecommerce.api.user.domain.exception;

import com.utepinos.utp_ecommerce.api.shared.domain.exception.ErrorException;

import lombok.Getter;

/**
 * * Excepción cuando se está registrando un usuario.
 */
@Getter
public final class ServerErrorWhileRegistering extends ErrorException {
  private String message = "Algo ocurrió al intentar registrarte";
  private int status = 500;
}
