package com.utepinos.utp_ecommerce.api.user.adapter.in.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.utepinos.utp_ecommerce.api.auth.core.application.port.out.GetCurrentUserPort;
import com.utepinos.utp_ecommerce.api.auth.core.domain.model.CurrentUser;
import com.utepinos.utp_ecommerce.api.shared.adapter.in.view.View;
import com.utepinos.utp_ecommerce.api.user.application.in.CreateUserPort;
import com.utepinos.utp_ecommerce.api.user.application.in.FindUserPort;
import com.utepinos.utp_ecommerce.api.user.domain.model.User;
import com.utepinos.utp_ecommerce.api.user.domain.request.CreateUserRequest;

import lombok.RequiredArgsConstructor;

/**
 * User Controller
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final CreateUserPort createUserPort;
  private final FindUserPort findUserPort;
  private final GetCurrentUserPort getCurrentUserPort;

  @PostMapping
  @JsonView(View.Public.class) // ! Gracias a esto solo muestra datos públicos (sin password)

  public ResponseEntity<User> create(@RequestBody CreateUserRequest request) {
    User user = createUserPort.create(request);
    return ResponseEntity.ok(user);
  }

  @GetMapping("/{id}")
  @JsonView(View.Public.class) // ! Gracias a esto solo muestra datos públicos (sin password)
  public ResponseEntity<User> findById(@PathVariable Long id) {
    return findUserPort.getById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/current")
  public ResponseEntity<String> current() {
    try {
      CurrentUser currentUser = getCurrentUserPort.get();
      return ResponseEntity.ok(currentUser.getUser().getEmail());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token inválido");
    }
  }
}
