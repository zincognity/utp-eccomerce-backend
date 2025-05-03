package com.utepinos.utp_ecommerce.api.user.adapter.in.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utepinos.utp_ecommerce.api.user.application.in.CreateUserPort;
import com.utepinos.utp_ecommerce.api.user.domain.model.User;
import com.utepinos.utp_ecommerce.api.user.domain.request.CreateUserRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final CreateUserPort createUserPort;

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
    User user = createUserPort.create(request);
    return ResponseEntity.ok(user);
  }
}
