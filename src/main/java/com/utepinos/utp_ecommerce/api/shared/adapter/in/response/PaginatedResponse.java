package com.utepinos.utp_ecommerce.api.shared.adapter.in.response;

import java.util.List;

import com.utepinos.utp_ecommerce.api.shared.domain.query.Paginated;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * * Estructura de una respuesta en formato página.
 */
@Getter
@SuperBuilder
public class PaginatedResponse<T> {
  private final int page;
  private final int size;
  private final int totalPages;
  private final long totalElements;
  private final String message;
  private final List<T> content;

  public static <T> PaginatedResponse<T> from(Paginated<T> paginated, String message) {
    return PaginatedResponse.<T>builder()
        .page(paginated.getPage())
        .size(paginated.getSize())
        .totalPages(paginated.getTotalPages())
        .totalElements(paginated.getTotalElements())
        .content(paginated.getContent())
        .message(message)
        .build();
  }
}
