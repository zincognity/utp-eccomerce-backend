package com.utepinos.utp_ecommerce.api.shared.adapter.in.response;

import static com.utepinos.utp_ecommerce.api.shared.domain.util.CaseConverterUtils.toSnakeCase;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * * Estructura de una respuesta más detallada de los errores.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailedFailureResponse {
  private String message;
  private List<Detail> details;

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Detail {
    private String field;
    private List<String> messages;

    public String getField() {
      return toSnakeCase(field);
    }
  }
}
