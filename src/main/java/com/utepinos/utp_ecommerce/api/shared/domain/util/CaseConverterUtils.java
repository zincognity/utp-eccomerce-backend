package com.utepinos.utp_ecommerce.api.shared.domain.util;

/**
 * * Convertidores de Strings en Case.
 */
public final class CaseConverterUtils {
  private CaseConverterUtils() {
  }

  /**
   * * Convierte un String en camel case.
   * 
   * @param field {@link String} Field.
   * @return {@link String} Camel case. `null` si el valor es `null`.
   */
  public static String toCamelCase(String field) {
    if (field == null)
      return null;

    String[] parts = field.split("_");
    StringBuilder camelCaseString = new StringBuilder(parts[0]);

    for (int i = 0; i < parts.length - 1; i++) {
      String part = parts[i + 1];
      camelCaseString.append(part.substring(0, 1).toUpperCase()).append(part.substring(1));
    }

    return camelCaseString.toString();
  }

  /**
   * * Convierte un String en snake case.
   *
   * @param field {@link String} field.
   * @return {@link String } Snake case. `null` si el valor es `null`.
   */
  public static String toSnakeCase(String field) {
    if (field == null) {
      return null;
    }

    return field.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
  }
}
