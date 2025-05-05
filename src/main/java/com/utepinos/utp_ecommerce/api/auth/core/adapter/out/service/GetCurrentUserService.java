package com.utepinos.utp_ecommerce.api.auth.core.adapter.out.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.utepinos.utp_ecommerce.api.auth.core.adapter.out.exception.GetUserWhenDoNotLoggedInException;
import com.utepinos.utp_ecommerce.api.auth.core.application.port.out.GetCurrentUserPort;
import com.utepinos.utp_ecommerce.api.auth.core.domain.model.CurrentUser;
import com.utepinos.utp_ecommerce.api.user.domain.model.User;

import lombok.RequiredArgsConstructor;

/**
 * * Implementación del servicio para obtener el usuario por Authentication
 * * context (Bearer)
 */
@Service
@RequiredArgsConstructor
public class GetCurrentUserService implements GetCurrentUserPort {

  /**
   * * Devuelve el usuario actual.
   */
  @Override
  public CurrentUser get() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated())
      throw new GetUserWhenDoNotLoggedInException();

    Object principal = authentication.getPrincipal();

    if (principal instanceof String && "anonymousUser".equals(principal))
      throw new GetUserWhenDoNotLoggedInException();

    if (principal instanceof User user)
      return new CurrentUser(user);

    throw new GetUserWhenDoNotLoggedInException();
  }
}
