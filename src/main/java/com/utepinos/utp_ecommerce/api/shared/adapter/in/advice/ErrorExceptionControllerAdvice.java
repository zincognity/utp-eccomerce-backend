package com.utepinos.utp_ecommerce.api.shared.adapter.in.advice;

import static com.utepinos.utp_ecommerce.api.shared.adapter.in.util.ResponseShortcuts.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.utepinos.utp_ecommerce.api.shared.adapter.in.response.FailureResponse;
import com.utepinos.utp_ecommerce.api.shared.domain.exception.ErrorException;
import com.utepinos.utp_ecommerce.api.shared.domain.exception.InternalServerErrorException;
import com.utepinos.utp_ecommerce.api.shared.domain.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * * Controlador global para el manejo de errores basado en el cuerpo de la
 * * respuesta.
 */
@Slf4j
@RestControllerAdvice
public final class ErrorExceptionControllerAdvice {
  /**
   * * Handler para los errores con instancia {@link ErrorException}.
   *
   * @param err {@link ErrorException} La excepción.
   * @return {@link ResponseEntity} La respuesta con el mensaje de error.
   */
  @ExceptionHandler(ErrorException.class)
  public ResponseEntity<FailureResponse> handle(final ErrorException err) {
    return error(err);
  }

  /**
   * * Handler para los errores con instancia
   * {@link InternalServerErrorException}.
   *
   * @param err {@link InternalServerErrorException} La excepción.
   * @return {@link ResponseEntity} La respuesta con el mensaje de error.
   */
  @ExceptionHandler(InternalServerErrorException.class)
  public ResponseEntity<FailureResponse> handle(final InternalServerErrorException err) {
    log.error("", err.getCause());

    return error(err);
  }

  /**
   * * Handler para los errores con instancia {@link NotFoundException}.
   *
   * @param err {@link NotFoundException} La excepción.
   * @return {@link ResponseEntity} La respuesta con el mensaje de error.
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<FailureResponse> handle(final NotFoundException err) {
    return error(err);
  }
}
