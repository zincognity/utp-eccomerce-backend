package com.utepinos.utp_ecommerce.api.user.application.in;

import com.utepinos.utp_ecommerce.api.user.domain.model.User;
import com.utepinos.utp_ecommerce.api.user.domain.request.CreateUserRequest;

/**
 * * Interfaz de los métodos de creación de usuarios.
 */
public interface CreateUserPort {
  /**
   * * Método para crear un nuevo usuario.
   * @param request {@link CreateUserRequest} La request.
   * @return {@link User} El usuario.
   */
  User create(CreateUserRequest request);
}
