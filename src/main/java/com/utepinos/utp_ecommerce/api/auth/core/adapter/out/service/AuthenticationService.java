package com.utepinos.utp_ecommerce.api.auth.core.adapter.out.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * * Implementación del servicio de autenticación.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final AuthenticationManager authenticationManager;

  /**
   * * Autentica al usuario utilizando el gestor de autenticación.
   *
   * @param username {@link String} El nombre de usuario del usuario.
   * @param password {@link String} La contraseña del usuario.
   */
  public void authenticate(String username, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
  }
}
