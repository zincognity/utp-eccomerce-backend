package com.utepinos.utp_ecommerce.api.user.domain.exception;

import lombok.Getter;

@Getter
public class EmailAlreadyInUseException extends Exception {
  private final String message = "El email ya se encuentra en uso";
  private final int status = 409;
}
