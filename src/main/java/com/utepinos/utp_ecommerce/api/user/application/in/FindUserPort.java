package com.utepinos.utp_ecommerce.api.user.application.in;

import java.util.Optional;

import com.utepinos.utp_ecommerce.api.user.domain.model.User;

/**
 * * Interfaz de los métodos de búsqueda de usuarios.
 */
public interface FindUserPort {

  /**
   * * Método para obtener un usuario por su correo.
   *
   * @param email {@link String} El correo.
   * @return {@link User} El usuario.
   */
  Optional<User> getByEmail(String email);

  /**
   * * Método para obtener un usuario por su id.
   *
   * @param id {@link Long} El id.
   * @return {@link User} El usuario.
   */
  Optional<User> getById(Long id);

  /**
   * * Método para saber si un usuario existe (correo).
   * @param email {@link String} El correo.
   * @return {@link Boolean} La respuesta en boolean.
   */
  Boolean exists(String email);

  /**
   * * Método para saber si un usuario existe (id).
   * @param id {@link Long} El id.
   * @return {@link Boolean} La respuesta en boolean.
   */
  Boolean exists(Long id);
}
