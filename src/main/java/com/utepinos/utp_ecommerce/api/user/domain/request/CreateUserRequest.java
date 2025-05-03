package com.utepinos.utp_ecommerce.api.user.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * * Estructura para enviar request de creación de usuarios.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateUserRequest {
  private String name;
  private String email;
  private String phone;
  private String password;
}
