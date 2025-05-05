package com.utepinos.utp_ecommerce.api.shared.adapter.in.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * * Estructura de una respuesta básica.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicResponse {
    private String message;
}
