package com.utepinos.utp_ecommerce.api.auth.core.application.port.in;

import com.utepinos.utp_ecommerce.api.auth.core.domain.model.Jwt;
import com.utepinos.utp_ecommerce.api.auth.core.domain.request.RegisterUserRequest;

/**
 * * Interfaz de los métodos para el registro de usuarios.
 */
public interface RegisterUserPort {

  /**
   * * Método para registrar un usuario.
   * 
   * @param request {@link RegisterUserRequest} La request.
   * @return {@link Jwt} El JWT.
   */
  Jwt register(RegisterUserRequest request);
}
