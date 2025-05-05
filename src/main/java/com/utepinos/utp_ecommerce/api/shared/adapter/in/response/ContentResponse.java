package com.utepinos.utp_ecommerce.api.shared.adapter.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * * Estructura de una respuesta con un mensaje y contenido.
 */
@Getter
@SuperBuilder
public class ContentResponse<T> {
    private final String message;
    private final T content;
}
