package com.utepinos.utp_ecommerce.api.user.application.in;

import com.utepinos.utp_ecommerce.api.user.domain.model.User;
import com.utepinos.utp_ecommerce.api.user.domain.request.CreateUserRequest;

public interface CreateUserPort {
  User create(CreateUserRequest request);
}
