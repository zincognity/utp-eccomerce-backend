package com.utepinos.utp_ecommerce.api.shared.domain.util;

/**
 * * Clase de utilidades para operaciones con Strings.
 */
public class StringUtils {
  /**
   * * Retorna null si los valores están en blank (" ").
   *
   * @param value {@link String} Los valores a evaluar.
   * @return {@link String} Null si el valor está en black, si no el valor mismo.
   */
  public static String toNullIfBlank(String value) {
    return value != null && value.isBlank() ? null : value;
  }
}
