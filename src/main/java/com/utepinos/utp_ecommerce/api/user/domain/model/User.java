package com.utepinos.utp_ecommerce.api.user.domain.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class User implements UserDetails {
  private Long id;

  private String name;

  private String email;

  private String phone;

  private UserRole role;

  @JsonView(View.Internal.class) // ! Esto determina que el dato es un tipo internal o privado.
  private String password;

  @JsonView(View.Redundancy.class)
  private List<GrantedAuthority> authorities;

  @JsonView(View.Redundancy.class)
  private Boolean accountNonLocked;

  @JsonView(View.Redundancy.class)
  private Boolean accountNonExpired;

  @JsonView(View.Redundancy.class)
  private Boolean credentialsNonExpired;

  @JsonView(View.Redundancy.class)
  private Boolean enabled;

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  /**
   * * Obtiene el nombre de usuario del usuario.
   *
   * @return {@link String} El nombre de usuario.
   */
  @Override
  public String getUsername() {
    return email;
  }

  /**
   * * Obtiene los roles asignados al usuario.
   *
   * @return {@link Collection} Lista de los roles asignados al usuario.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }
}
