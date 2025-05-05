package com.utepinos.utp_ecommerce.api.shared.domain.util;

import java.sql.Array;
import java.util.Arrays;
import java.util.function.Function;

/**
 * * Utilidades para trabajar con clases enum.
 */
public class EnumUtils {
  /**
   * * Retorna los nombres de los valores del enum en uppercase como por defecto.
   *
   * @param e {@link Class} La clase enum.
   * @return {@link Array} Los nombres de los valores del enum en uppercase.
   */
  public static String[] getEnumNames(Class<? extends Enum<?>> e) {
    return getEnumNames(e, String::toUpperCase);
  }

  /**
   * * Retona los nombres transformados de una clase Enum basado en un
   * * transformador.
   *
   * @param e               {@link Class} La clase enum.
   * @param nameTransformer {@link Function} Una función que transforma los
   *                        nombres de los valores del enum.
   * @return {@link Array} Los nombres de los valores transformados.
   */
  public static String[] getEnumNames(Class<? extends Enum<?>> e, Function<String, String> nameTransformer) {
    return Arrays.stream(e.getEnumConstants())
        .map(Enum::name)
        .map(nameTransformer)
        .toArray(String[]::new);
  }
}
