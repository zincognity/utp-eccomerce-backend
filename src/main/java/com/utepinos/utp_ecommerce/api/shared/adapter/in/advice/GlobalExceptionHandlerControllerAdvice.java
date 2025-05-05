package com.utepinos.utp_ecommerce.api.shared.adapter.in.advice;

import static com.utepinos.utp_ecommerce.api.shared.adapter.in.util.ResponseShortcuts.badRequest;
import static com.utepinos.utp_ecommerce.api.shared.adapter.in.util.ResponseShortcuts.forbidden;
import static com.utepinos.utp_ecommerce.api.shared.adapter.in.util.ResponseShortcuts.internalServerError;
import static com.utepinos.utp_ecommerce.api.shared.adapter.in.util.ResponseShortcuts.methodNotAllowed;
import static com.utepinos.utp_ecommerce.api.shared.adapter.in.util.ResponseShortcuts.notImplemented;
import static com.utepinos.utp_ecommerce.api.shared.adapter.in.util.ResponseShortcuts.validationError;

import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.utepinos.utp_ecommerce.api.auth.core.adapter.out.exception.GetUserWhenDoNotLoggedInException;
import com.utepinos.utp_ecommerce.api.shared.adapter.in.response.DetailedFailureResponse;
import com.utepinos.utp_ecommerce.api.shared.domain.validation.ValidationError;

import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;

/**
 * * Controlador global de excepciones que maneja errores genéricos,
 * * de validación, deserialización y de seguridad.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerControllerAdvice {
  @Value("${spring.profiles.active:dev}")
  private List<String> profileActive;

  /**
   * * Handler de excepciones no controladas {@link Exception}.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception err) {
    return internalServerError();
  }

  /**
   * * Handler de errores de servlet {@link ServletException}.
   */
  @ExceptionHandler(ServletException.class)
  public ResponseEntity<?> handleServletException(ServletException e) {
    return forbidden();
  }

  /**
   * * Handler de recursos no encontrados {@link NoResourceFoundException}.
   */
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException e) {
    return forbidden();
  }

  /**
   * * Handler de métodos http request no soportados
   * * {@link HttpRequestMethodNotSupportedException}.
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    if (profileActive.contains("dev"))
      return methodNotAllowed();

    return forbidden();
  }

  /**
   * * Handler de excepciones no soportadas {@link UnsupportedOperationException}.
   */
  @ExceptionHandler(UnsupportedOperationException.class)
  public ResponseEntity<?> handleUnsupportedOperationException(UnsupportedOperationException e) {
    if (profileActive.contains("dev"))
      return notImplemented();

    return forbidden();
  }

  /**
   * * Handler de excepciones cuando el usuario no está logeado
   * * {@link GetUserWhenDoNotLoggedInException}.
   */
  @ExceptionHandler(GetUserWhenDoNotLoggedInException.class)
  public ResponseEntity<?> handleGetUserWhenDoNotLoggedInException(GetUserWhenDoNotLoggedInException e) {
    log.error("Get user when do not logged in: ", e);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

  /**
   * * Handler de parámetros incorrectos al enviar una request
   * * {@link HttpMessageNotReadableException}.
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    if (e.getMostSpecificCause() instanceof JsonParseException jsonParseException) {
      JsonLocation jsonLocation = jsonParseException.getLocation();

      return badRequest("Error al convertir el JSON en la fila " + jsonLocation.getLineNr() +
          ", columna " + jsonLocation.getColumnNr());
    }

    else if (e.getMostSpecificCause() instanceof DateTimeParseException dateTimeParseException) {
      return badRequest("No se puede convertir a fecha: " + dateTimeParseException.getMessage());
    }

    else if (e.getMostSpecificCause() instanceof InvalidTypeIdException invalidTypeIdException) {
      String originalMessage = invalidTypeIdException.getLocalizedMessage();
      String information = originalMessage.substring(
          originalMessage.indexOf("[") + 1,
          originalMessage.indexOf("]"));

      return badRequest("Solo los siguientes valores son permitidos: " + information);
    }

    else if (e.getMostSpecificCause() instanceof UnrecognizedPropertyException unrecognizedPropertyException) {
      String fieldName = unrecognizedPropertyException.getPropertyName();

      return badRequest("Campo no reconocido: " + fieldName);
    }

    log.error("Http message not readable: ", e);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  /**
   * * Handler de acceso denegado {@link AuthorizationDeniedException}.
   */
  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<?> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
    return forbidden("Acceso denegado");
  }

  /**
   * * Handler que maneja errores cuando falta una parte del cuerpo
   * * multipart/form-data. {@link MissingServletRequestPartException}.
   */
  @ExceptionHandler(MissingServletRequestPartException.class)
  public ResponseEntity<DetailedFailureResponse> handle(final MissingServletRequestPartException e) {
    String name = e.getRequestPartName();

    if ("imageFile".equals(name)) {
      name = "Imagen";
    }

    return validationError(
        List.of(new ValidationError("param." + e.getRequestPartName(), name + " es requerido(a)")));
  }
}
