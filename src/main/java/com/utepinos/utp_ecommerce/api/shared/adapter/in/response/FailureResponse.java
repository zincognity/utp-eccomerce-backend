package com.utepinos.utp_ecommerce.api.shared.adapter.in.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * * Estructura de una respuesta para enviar respuestas de errores básicos.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FailureResponse {
  private String message;
}
