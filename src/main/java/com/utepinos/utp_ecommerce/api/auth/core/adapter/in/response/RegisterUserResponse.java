package com.utepinos.utp_ecommerce.api.auth.core.adapter.in.response;

import com.utepinos.utp_ecommerce.api.auth.core.domain.model.Jwt;
import com.utepinos.utp_ecommerce.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * * Estructura de la respuesta esperada cuando se un usuario es registrado
 * * correctamente.
 */
@SuperBuilder
public final class RegisterUserResponse extends ContentResponse<Jwt> {
}
