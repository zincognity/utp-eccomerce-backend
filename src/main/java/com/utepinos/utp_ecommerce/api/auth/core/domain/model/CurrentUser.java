package com.utepinos.utp_ecommerce.api.auth.core.domain.model;

import com.utepinos.utp_ecommerce.api.user.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * * Estructura de un usuario que está logeado.
 */
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class CurrentUser {
  private final User user;
}
