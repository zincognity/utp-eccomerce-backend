package com.utepinos.utp_ecommerce.api.auth.core.application.port.in;

import com.utepinos.utp_ecommerce.api.auth.core.domain.model.Jwt;
import com.utepinos.utp_ecommerce.api.auth.core.domain.request.LoginUserRequest;

/**
 * * Interfaz de los método para el logueo de usuarios.
 */
public interface LoginUserPort {
  /**
   * * Método para logear a un usuario.
   * 
   * @param request {@link LoginUserRequest} La request.
   * @return {@link Jwt} El JWT.
   */
  Jwt login(LoginUserRequest request);
}
