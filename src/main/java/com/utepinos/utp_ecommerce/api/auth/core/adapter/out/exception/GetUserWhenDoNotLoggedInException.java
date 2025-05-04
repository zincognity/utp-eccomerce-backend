package com.utepinos.utp_ecommerce.api.auth.core.adapter.out.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * * Excepción cuando el usuario no ha sido logeado.
 */
@Getter
@NoArgsConstructor
public final class GetUserWhenDoNotLoggedInException extends RuntimeException {
  private final String message = "Usuario no logeado";
  private final int status = 401;
}
