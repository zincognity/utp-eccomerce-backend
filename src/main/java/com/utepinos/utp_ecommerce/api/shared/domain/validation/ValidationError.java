package com.utepinos.utp_ecommerce.api.shared.domain.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * * Representa un error de validación asociado a un campo específico.
 *
 * <p>
 * Esta clase se utiliza para comunicar errores ocurridos durante la validación
 * de entradas o datos de usuario, especificando el campo afectado y el mensaje
 * de error correspondiente.
 * </p>
 */
@Getter
@ToString
@RequiredArgsConstructor
public final class ValidationError {
  private final String field;
  private final String message;
}
