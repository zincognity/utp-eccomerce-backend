package com.utepinos.utp_ecommerce.api.auth.core.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequest {
  private String email;
  private String password;
}
