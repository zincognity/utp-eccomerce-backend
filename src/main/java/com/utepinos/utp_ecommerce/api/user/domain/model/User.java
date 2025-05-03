package com.utepinos.utp_ecommerce.api.user.domain.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.utepinos.utp_ecommerce.api.shared.adapter.in.view.View;
import com.utepinos.utp_ecommerce.api.user.domain.model.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * * Estructura de la tabla usuarios.
 */
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonView(View.Public.class)
public class User {
  private Long id;
  private String name;
  private String email;
  private String phone;
  @JsonView(View.Internal.class) // ! Esto determina que el dato es un tipo internal o privado.
  private String password;
  @JsonView(View.Internal.class) // ! Esto determina que el dato es un tipo internal o privado.
  private UserRole role;
}
