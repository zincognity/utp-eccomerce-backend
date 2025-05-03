package com.utepinos.utp_ecommerce.api.shared.adapter.in.view;

/**
 * * Views para mantener datos según el nivel de exposición.
 */
public class View {

  /**
   * * Define los datos públicos.
   */
  public static class Public {
  };

  /**
   * * Define los datos internos.
   */
  public static class Internal extends Public {
  };

  /**
   * * Define los datos para evitar redundancias o bucles infinitos.
   */
  public static class Redundancy extends Internal {
  };
}
