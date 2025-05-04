package com.utepinos.utp_ecommerce.api.auth.core.adapter.in.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utepinos.utp_ecommerce.api.auth.core.application.port.in.LoginUserPort;
import com.utepinos.utp_ecommerce.api.auth.core.application.port.in.RegisterUserPort;
import com.utepinos.utp_ecommerce.api.auth.core.domain.request.LoginUserRequest;
import com.utepinos.utp_ecommerce.api.user.domain.request.CreateUserRequest;

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
  public ResponseEntity<String> register(@RequestBody CreateUserRequest request) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(registerUserPort.register(request));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Algo ha ocurrido al registrarte");
    }
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginUserRequest request) {
    try {
      String jwt = loginUserPort.login(request);
      return ResponseEntity.status(HttpStatus.OK).body(jwt);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
  }
}
