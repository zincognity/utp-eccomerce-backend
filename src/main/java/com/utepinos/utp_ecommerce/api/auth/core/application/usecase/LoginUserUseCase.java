package com.utepinos.utp_ecommerce.api.auth.core.application.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.utepinos.utp_ecommerce.api.auth.core.adapter.out.service.AuthenticationService;
import com.utepinos.utp_ecommerce.api.auth.core.application.port.in.LoginUserPort;
import com.utepinos.utp_ecommerce.api.auth.core.domain.request.LoginUserRequest;
import com.utepinos.utp_ecommerce.api.shared.domain.util.JwtUtil;
import com.utepinos.utp_ecommerce.api.user.application.in.FindUserPort;
import com.utepinos.utp_ecommerce.api.user.domain.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public final class LoginUserUseCase implements LoginUserPort {
  private final JwtUtil jwtUtil;
  private final AuthenticationService authenticationService;
  private final FindUserPort findUserPort;

  @Override
  public String login(LoginUserRequest request) {
    authenticationService.authenticate(request.getEmail(), request.getPassword());

    Optional<User> user = findUserPort.getByEmail(request.getEmail());
    String token = jwtUtil.generateToken(user.get());
    return token;
  }

}
