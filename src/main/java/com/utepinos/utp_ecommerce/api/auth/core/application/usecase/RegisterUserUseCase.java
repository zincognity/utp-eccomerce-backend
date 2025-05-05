package com.utepinos.utp_ecommerce.api.auth.core.application.usecase;

import org.springframework.stereotype.Service;

import com.utepinos.utp_ecommerce.api.auth.core.adapter.out.service.AuthenticationService;
import com.utepinos.utp_ecommerce.api.auth.core.application.port.in.RegisterUserPort;
import com.utepinos.utp_ecommerce.api.auth.core.domain.model.Jwt;
import com.utepinos.utp_ecommerce.api.auth.core.domain.request.RegisterUserRequest;
import com.utepinos.utp_ecommerce.api.shared.domain.util.JwtUtils;
import com.utepinos.utp_ecommerce.api.user.application.in.CreateUserPort;
import com.utepinos.utp_ecommerce.api.user.domain.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * * Casos de uso para registrar un usuario.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public final class RegisterUserUseCase implements RegisterUserPort {
  private final JwtUtils jwtUtil;
  private final CreateUserPort createUserPort;
  private final AuthenticationService authenticationService;

  @Override
  public Jwt register(RegisterUserRequest request) {
    User userCreated = createUserPort.create(request);
    authenticationService.authenticate(userCreated.getEmail(), request.getPassword());
    return Jwt.builder().jwt(jwtUtil.generateToken(userCreated)).build();
  }
}
