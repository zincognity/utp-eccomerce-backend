package com.utepinos.utp_ecommerce.api.auth.core.adapter.in.controller;

import static com.utepinos.utp_ecommerce.api.shared.adapter.in.util.ResponseShortcuts.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utepinos.utp_ecommerce.api.auth.core.adapter.in.response.LoginUserResponse;
import com.utepinos.utp_ecommerce.api.auth.core.adapter.in.response.RegisterUserResponse;
import com.utepinos.utp_ecommerce.api.auth.core.application.port.in.LoginUserPort;
import com.utepinos.utp_ecommerce.api.auth.core.application.port.in.RegisterUserPort;
import com.utepinos.utp_ecommerce.api.auth.core.domain.exception.InvalidCredentialsException;
import com.utepinos.utp_ecommerce.api.auth.core.domain.request.LoginUserRequest;
import com.utepinos.utp_ecommerce.api.auth.core.domain.request.RegisterUserRequest;
import com.utepinos.utp_ecommerce.api.user.domain.exception.EmailAlreadyInUseException;

import lombok.RequiredArgsConstructor;

/**
 * * Controlador de endpoints para la autenticación del usuario.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final RegisterUserPort registerUserPort;
  private final LoginUserPort loginUserPort;

  @PostMapping("/register")
  public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest request)
      throws EmailAlreadyInUseException {
    return ok(() -> registerUserPort.register(request), "Usuario registrado exitosamente");
  }

  @PostMapping("/login")
  public ResponseEntity<LoginUserResponse> login(@RequestBody LoginUserRequest request)
      throws InvalidCredentialsException {
    return ok(() -> loginUserPort.login(request), "Usuario logeado exitosamente");
  }
}
