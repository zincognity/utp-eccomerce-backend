package com.utepinos.utp_ecommerce.api.auth.core.application.port.in;

import com.utepinos.utp_ecommerce.api.auth.core.domain.model.Jwt;
import com.utepinos.utp_ecommerce.api.user.domain.request.CreateUserRequest;

/**
 * * Interfaz de los métodos para el registro de usuarios.
 */
public interface RegisterUserPort {

  /**
   * * Método para registrar un usuario.
   * 
   * @param request {@link CreateUserRequest} La request.
   * @return {@link Jwt} El jwt.
   */
  String register(CreateUserRequest request);
}
