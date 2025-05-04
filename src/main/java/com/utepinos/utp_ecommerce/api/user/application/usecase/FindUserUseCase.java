package com.utepinos.utp_ecommerce.api.user.application.usecase;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.utepinos.utp_ecommerce.api.user.application.in.FindUserPort;
import com.utepinos.utp_ecommerce.api.user.domain.model.User;
import com.utepinos.utp_ecommerce.api.user.domain.model.enums.UserRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * * Casos de uso para buscar usuarios.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public final class FindUserUseCase implements FindUserPort {
  private final JdbcTemplate jdbcTemplate;

  @Override
  public Optional<User> getByEmail(String email) {
    List<User> users = jdbcTemplate.query(
        "SELECT id, name, email, phone, role, password FROM users WHERE email = ?",
        (res, rowNum) -> User.builder()
            .id(res.getLong("id"))
            .name(res.getString("name"))
            .email(res.getString("email"))
            .phone(res.getString("phone"))
            .role(UserRole.valueOf(res.getString("role")))
            .password(res.getString("password"))
            .build(),
        email);

    return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
  }

  @Override
  public Optional<User> getById(Long id) {
    List<User> users = jdbcTemplate.query(
        "SELECT id, name, email, phone FROM users WHERE id = ?",
        (res, rowNum) -> User.builder()
            .id(res.getLong("id"))
            .name(res.getString("name"))
            .email(res.getString("email"))
            .phone(res.getString("phone"))
            .build(),
        id);

    return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
  }

  @Override
  public Boolean exists(String email) {
    Optional<User> user = getByEmail(email);

    if (user.isEmpty())
      return false;
    return true;
  }

  @Override
  public Boolean exists(Long id) {
    Optional<User> user = getById(id);

    if (user.isEmpty())
      return false;
    return true;
  }
}
