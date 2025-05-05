package com.utepinos.utp_ecommerce.api.auth.core.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * * Estructura para enviar request del registro de usuarios.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RegisterUserRequest {
  private String name;
  private String email;
  private String phone;
  private String password;
}
