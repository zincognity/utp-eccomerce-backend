package com.utepinos.utp_ecommerce.api.user.application.usecase;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.utepinos.utp_ecommerce.api.user.application.in.CreateUserPort;
import com.utepinos.utp_ecommerce.api.user.domain.model.User;
import com.utepinos.utp_ecommerce.api.user.domain.request.CreateUserRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUserUseCase implements CreateUserPort {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public User create(CreateUserRequest request) {
    jdbcTemplate.update(
        "INSERT INTO users (name, email, phone, password) VALUES (?, ?, ?, ?)",
        request.getName(),
        request.getEmail(),
        request.getPhone(),
        request.getPassword());

    var users = jdbcTemplate.query(
        "SELECT id, name, email, phone, password FROM users WHERE email = ?",
        (rs, rowNum) -> User.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .email(rs.getString("email"))
            .phone(rs.getString("phone"))
            .password(rs.getString("password"))
            .build(),
        request.getEmail());

    if (users.isEmpty()) {
      log.warn("User not found after insert with email: {}", request.getEmail());
      throw new IllegalStateException("User creation failed");
    }

    User newUser = users.get(0);
    log.info("User created: {}", newUser);
    return newUser;
  }
}
