package com.utepinos.utp_ecommerce.api.auth.core.application.usecase;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.utepinos.utp_ecommerce.api.auth.core.adapter.out.service.AuthenticationService;
import com.utepinos.utp_ecommerce.api.auth.core.application.port.in.RegisterUserPort;
import com.utepinos.utp_ecommerce.api.shared.domain.util.JwtUtil;
import com.utepinos.utp_ecommerce.api.user.application.in.CreateUserPort;
import com.utepinos.utp_ecommerce.api.user.domain.model.User;
import com.utepinos.utp_ecommerce.api.user.domain.request.CreateUserRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * * Casos de uso para registrar un usuario.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public final class RegisterUserUseCase implements RegisterUserPort {
  private final JwtUtil jwtUtil;
  private final CreateUserPort createUserPort;
  private final AuthenticationService authenticationService;

  @Override
  public String register(CreateUserRequest request) {
    try {
      User userCreated = createUserPort.create(request);
      authenticationService.authenticate(userCreated.getEmail(), request.getPassword());
      return jwtUtil.generateToken(userCreated);
    } catch (BadCredentialsException | UsernameNotFoundException e) {
      System.out.println(e.getMessage());
      throw new BadCredentialsException("Incorrect username or password");
    }
  }
}
