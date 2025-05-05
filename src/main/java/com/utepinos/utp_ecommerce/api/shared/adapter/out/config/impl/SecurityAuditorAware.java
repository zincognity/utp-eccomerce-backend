package com.utepinos.utp_ecommerce.api.shared.adapter.out.config.impl;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

import com.utepinos.utp_ecommerce.api.auth.core.application.port.out.GetCurrentUserPort;
import com.utepinos.utp_ecommerce.api.user.domain.model.User;

import lombok.RequiredArgsConstructor;

/**
 * Auditor que obtiene el usuario actual en base al JWT.
 */
@RequiredArgsConstructor
public class SecurityAuditorAware implements AuditorAware<User> {
  private final GetCurrentUserPort getCurrentUserPort;

  @Override
  public @NonNull Optional<User> getCurrentAuditor() {
    return Optional.ofNullable(getCurrentUserPort.get().getUser());
  }
}
