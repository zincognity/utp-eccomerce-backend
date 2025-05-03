package com.utepinos.utp_ecommerce.api.user.domain.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.utepinos.utp_ecommerce.api.shared.adapter.in.view.View;

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
  @JsonView(View.Internal.class)
  private String password;
}
