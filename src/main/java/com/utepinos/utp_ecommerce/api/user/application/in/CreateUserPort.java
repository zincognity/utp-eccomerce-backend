package com.utepinos.utp_ecommerce.api.user.application.in;

import com.utepinos.utp_ecommerce.api.auth.core.domain.request.RegisterUserRequest;
import com.utepinos.utp_ecommerce.api.user.domain.model.User;

/**
 * * Interfaz de los métodos de creación de usuarios.
 */
public interface CreateUserPort {
  /**
   * * Método para crear un nuevo usuario.
   *
   * @param request {@link RegisterUserRequest} La request.
   * @return {@link User} El usuario.
   */
  User create(RegisterUserRequest request);
}
