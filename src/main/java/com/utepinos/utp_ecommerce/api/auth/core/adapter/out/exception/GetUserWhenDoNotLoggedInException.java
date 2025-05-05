package com.utepinos.utp_ecommerce.api.auth.core.adapter.out.exception;

import com.utepinos.utp_ecommerce.api.shared.domain.exception.ErrorException;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * * Excepción cuando el usuario no ha sido logeado.
 */
@Getter
@NoArgsConstructor
public final class GetUserWhenDoNotLoggedInException extends ErrorException {
  private final String message = "Usuario no logeado";
  private final int status = 401;
}
