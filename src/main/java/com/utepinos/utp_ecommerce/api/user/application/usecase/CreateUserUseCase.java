package com.utepinos.utp_ecommerce.api.user.application.usecase;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.utepinos.utp_ecommerce.api.auth.core.domain.request.RegisterUserRequest;
import com.utepinos.utp_ecommerce.api.user.application.in.CreateUserPort;
import com.utepinos.utp_ecommerce.api.user.application.in.FindUserPort;
import com.utepinos.utp_ecommerce.api.user.domain.exception.EmailAlreadyInUseException;
import com.utepinos.utp_ecommerce.api.user.domain.exception.ServerErrorWhileRegistering;
import com.utepinos.utp_ecommerce.api.user.domain.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * * Casos de uso para crear usuarios.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public final class CreateUserUseCase implements CreateUserPort {

  private final JdbcTemplate jdbcTemplate;
  private final PasswordEncoder passwordEncoder;
  private final FindUserPort findUserPort;

  @Override
  public User create(RegisterUserRequest request) {
    if (findUserPort.exists(request.getEmail()))
      throw new EmailAlreadyInUseException();

    try {
      jdbcTemplate.update(
          "INSERT INTO users (name, email, phone, password) VALUES (?, ?, ?, ?)",
          request.getName(),
          request.getEmail(),
          request.getPhone(),
          passwordEncoder.encode(request.getPassword()));

      Optional<User> user = findUserPort.getByEmail(request.getEmail());

      log.info("User created: {}", user.get());
      return user.get();
    } catch (Exception e) {
      throw new ServerErrorWhileRegistering();
    }
  }
}
