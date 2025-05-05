package com.utepinos.utp_ecommerce.api.user.adapter.in.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.utepinos.utp_ecommerce.api.auth.core.adapter.out.exception.GetUserWhenDoNotLoggedInException;
import com.utepinos.utp_ecommerce.api.auth.core.application.port.out.GetCurrentUserPort;
import com.utepinos.utp_ecommerce.api.auth.core.domain.model.CurrentUser;
import com.utepinos.utp_ecommerce.api.auth.core.domain.request.RegisterUserRequest;
import com.utepinos.utp_ecommerce.api.shared.adapter.in.view.View;
import com.utepinos.utp_ecommerce.api.shared.domain.exception.ErrorException;
import com.utepinos.utp_ecommerce.api.user.application.in.CreateUserPort;
import com.utepinos.utp_ecommerce.api.user.application.in.FindUserPort;
import com.utepinos.utp_ecommerce.api.user.domain.model.User;

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

  public ResponseEntity<User> create(@RequestBody RegisterUserRequest request) {
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
  public ResponseEntity<String> current()
      throws ErrorException, AuthorizationDeniedException, GetUserWhenDoNotLoggedInException {
    CurrentUser currentUser = getCurrentUserPort.get();
    return ResponseEntity.ok(currentUser.getUser().getEmail());
  }
}
