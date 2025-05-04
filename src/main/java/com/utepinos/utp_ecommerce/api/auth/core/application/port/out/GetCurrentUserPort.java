package com.utepinos.utp_ecommerce.api.auth.core.application.port.out;

import com.utepinos.utp_ecommerce.api.auth.core.domain.model.CurrentUser;

/**
 * * Interfaz de los métodos para la obtención de un usuario logeado.
 */
public interface GetCurrentUserPort {
  /**
   * * Método para obtener el usuario autenticado.
   * 
   * @return {@link CurrentUser} El usuario logeado.
   * @throws {@link GetUserWhenDoNotLoggedInException} Si el usuario no se ha
   *                logeado.
   */
  CurrentUser get();
}
