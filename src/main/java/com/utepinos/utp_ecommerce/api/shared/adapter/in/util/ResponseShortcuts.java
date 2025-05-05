package com.utepinos.utp_ecommerce.api.shared.adapter.in.util;

import static com.utepinos.utp_ecommerce.api.shared.domain.util.CaseConverterUtils.toSnakeCase;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.data.relational.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.utepinos.utp_ecommerce.api.shared.adapter.in.response.BasicResponse;
import com.utepinos.utp_ecommerce.api.shared.adapter.in.response.ContentResponse;
import com.utepinos.utp_ecommerce.api.shared.adapter.in.response.DetailedFailureResponse;
import com.utepinos.utp_ecommerce.api.shared.adapter.in.response.DetailedFailureResponse.Detail;
import com.utepinos.utp_ecommerce.api.shared.adapter.in.response.FailureResponse;
import com.utepinos.utp_ecommerce.api.shared.adapter.in.response.PaginatedResponse;
import com.utepinos.utp_ecommerce.api.shared.domain.exception.ErrorException;
import com.utepinos.utp_ecommerce.api.shared.domain.exception.InternalServerErrorException;
import com.utepinos.utp_ecommerce.api.shared.domain.query.Paginated;
import com.utepinos.utp_ecommerce.api.shared.domain.validation.ValidationError;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * * Clase utilitaria para construir respuestas HTTP estándar y reutilizables.
 * * Centraliza la lógica común para respuestas exitosas, errores,
 * * validaciones y descargas de archivos.
 *
 * <p>
 * Contiene métodos para:
 * </p>
 * <ul>
 * <li>Respuestas 200 OK con contenido, sin contenido o paginadas</li>
 * <li>Respuestas con errores (400, 403, 422, 500, etc.)</li>
 * <li>Respuestas con validaciones detalladas</li>
 * <li>Respuestas con archivos descargables</li>
 * </ul>
 *
 * <p>
 * Ejemplo de uso:
 * </p>
 * 
 * <pre>{@code
 * return ResponseShortcuts.ok(() -> service.getData(), "Datos obtenidos correctamente");
 * }</pre>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseShortcuts {
  /**
   * * Crea una respuesta 200 OK con contenido y mensaje.
   *
   * @param bodySupplier {@link Supplier} Proveedor del contenido de la respuesta.
   * @param message      {@link String} Mensaje a incluir.
   * @return {@link ResponseEntity} con {@link ContentResponse}.
   */
  public static <T, R extends ContentResponse<T>> ResponseEntity<R> ok(
      Supplier<T> bodySupplier,
      String message) {

    T body = bodySupplier.get();

    @SuppressWarnings("unchecked")
    R response = (R) ContentResponse.<T>builder()
        .message(message)
        .content(body)
        .build();

    return ResponseEntity.ok(response);
  }

  /**
   * * Crea una respuesta 200 OK sin contenido, con un mensaje.
   *
   * @param runnable {@link Runnable} Acción a ejecutar.
   * @param message  {@link String} Mensaje de éxito.
   * @return {@link ResponseEntity} con {@link BasicResponse}.
   */
  public static ResponseEntity<BasicResponse> ok(
      Runnable runnable,
      String message) {

    runnable.run();

    return ResponseEntity.ok(
        BasicResponse.builder()
            .message(message)
            .build());
  }

  /**
   * * Crea una respuesta paginada 200 OK con contenido.
   *
   * @param page       {@link Integer} Número de página.
   * @param size       {@link Integer} Tamaño de página.
   * @param totalPages {@link Integer} Número total de páginas.
   * @param criteria   {@link Criteria} Criterios de búsqueda (opcional).
   * @param message    {@link String} Mensaje de respuesta.
   * @param content    {@link List} Contenido paginado.
   * @return {@link ResponseEntity} con {@link PaginatedResponse}.
   */
  public static <T, R extends PaginatedResponse<T>> ResponseEntity<R> okPaginated(
      int page,
      int size,
      int totalPages,
      Criteria criteria,
      String message,
      List<T> content) {

    Paginated<T> paginated = Paginated.<T>builder().page(page).size(size).totalElements(content.size())
        .totalPages(totalPages)
        .content(content).build();

    @SuppressWarnings("unchecked")
    R response = (R) PaginatedResponse.from(paginated, message);

    return ResponseEntity.ok(response);
  }

  /**
   * * Crea una respuesta 200 OK con archivo descargable.
   *
   * @param consumer       {@link Consumer} Función que escribe en el
   *                       OutputStream.
   * @param filename       {@link String} Nombre del archivo.
   * @param contentType    {@link String} Tipo MIME del archivo.
   * @param failureMessage {@link String} Mensaje en caso de error.
   * @return {@link ResponseEntity} con arreglo de bytes.
   */
  public static ResponseEntity<byte[]> ok(
      Consumer<OutputStream> consumer,
      String filename,
      String contentType,
      String failureMessage) {
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      consumer.accept(outputStream);

      return ResponseEntity.ok()
          .header("Content-Disposition", "attachment; filename=" + filename)
          .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
          .body(outputStream.toByteArray());
    } catch (ErrorException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error sending file");

      throw new InternalServerErrorException(e);
    }
  }

  /**
   * * Crea una respuesta 400 Bad Request con mensaje.
   *
   * @param message {@link String} Mensaje del error.
   * @return {@link ResponseEntity} con {@link FailureResponse}.
   */
  public static ResponseEntity<FailureResponse> badRequest(String message) {
    return error(message, HttpStatus.BAD_REQUEST);
  }

  /**
   * * Crea una respuesta de error genérica.
   *
   * @param message    {@link String} mensaje del error.
   * @param httpStatus {@link HttpStatus} código HTTP.
   * @return {@link ResponseEntity} con {@link FailureResponse}.
   */
  public static ResponseEntity<FailureResponse> error(String message, HttpStatus httpStatus) {
    return error(message, httpStatus.value());
  }

  /**
   * * Crea una respuesta de error genérica.
   *
   * @param message {@link String} Mensaje del error.
   * @param status  {@link Integer} Código HTTP (400–599).
   * @return {@link ResponseEntity} con {@link FailureResponse}.
   */
  public static ResponseEntity<FailureResponse> error(String message, int status) {
    Objects.requireNonNull(message, "The message cannot be null");
    if (status < 400 || status >= 600) {
      throw new IllegalArgumentException("The status must be a valid HTTP status code");
    }

    return ResponseEntity.status(status)
        .body(FailureResponse.builder()
            .message(message)
            .build());
  }

  /**
   * * Crea una respuesta de error basada en una excepción de dominio.
   *
   * @param exception {@link ErrorException} excepción que contiene el mensaje y
   *                  estado HTTP
   * @return {@link ResponseEntity} con {@link FailureResponse}.
   */
  public static ResponseEntity<FailureResponse> error(ErrorException exception) {
    Objects.requireNonNull(exception, "The exception cannot be null");

    return error(exception.getMessage(), exception.getStatus());
  }

  /**
   * * Crea una respuesta de error de validación con detalles por campo.
   *
   * @param message          {@link String} Mensaje general.
   * @param validationErrors {@link List} Lista de errores de validación.
   * @return {@link ResponseEntity} con {@link DetailedFailureResponse}.
   */
  public static ResponseEntity<DetailedFailureResponse> validationError(
      String message,
      List<ValidationError> validationErrors) {
    Objects.requireNonNull(message, "The message cannot be null");
    Objects.requireNonNull(validationErrors, "The validation errors cannot be null");

    List<Detail> details = new CopyOnWriteArrayList<>();

    for (ValidationError validationError : validationErrors) {
      String fieldSnakeCase = toSnakeCase(validationError.getField());

      Detail detail = details.stream()
          .filter(d -> d.getField().equals(fieldSnakeCase))
          .findFirst()
          .orElseGet(() -> {
            Detail newDetail = new Detail(fieldSnakeCase, new CopyOnWriteArrayList<>());
            details.add(newDetail);
            return newDetail;
          });

      detail.getMessages().add(validationError.getMessage());
    }

    return ResponseEntity.status(422)
        .body(DetailedFailureResponse.builder()
            .message(message)
            .details(details)
            .build());
  }

  /**
   * * Crea una respuesta 422 Unprocessable Entity con mensaje por defecto.
   *
   * @param validationErrors {@link List} errores de validación.
   * @return {@link ResponseEntity} con {@link DetailedFailureResponse}.
   */
  public static ResponseEntity<DetailedFailureResponse> validationError(
      List<ValidationError> validationErrors) {
    return validationError("Validación fallida", validationErrors);
  }

  /**
   * * Crea una respuesta 403 Forbidden con mensaje.
   *
   * @param message {@link String} Mensaje del error.
   * @return {@link ResponseEntity} con {@link FailureResponse}.
   */
  public static ResponseEntity<FailureResponse> forbidden(String message) {
    return error(message, HttpStatus.FORBIDDEN);
  }

  /**
   * * Crea una respuesta 403 Forbidden sin cuerpo.
   *
   * @return {@link ResponseEntity} vacío con estado 403.
   */
  public static ResponseEntity<Void> forbidden() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  /**
   * * Crea una respuesta 405 Method Not Allowed.
   *
   * @return {@link ResponseEntity} con mensaje de error.
   */
  public static ResponseEntity<FailureResponse> methodNotAllowed() {
    return error("Method not allowed", HttpStatus.METHOD_NOT_ALLOWED);
  }

  /**
   * * Crea una respuesta 500 Internal Server Error.
   *
   * @return {@link ResponseEntity} con mensaje genérico.
   */
  public static ResponseEntity<FailureResponse> internalServerError() {
    return internalServerError("Internal server error");
  }

  /**
   * * Crea una respuesta 500 Internal Server Error y registra la excepción.
   *
   * @param e {@link Exception} Excepción lanzada.
   * @return {@link ResponseEntity} con mensaje genérico.
   */
  public static ResponseEntity<FailureResponse> internalServerError(Exception e) {
    log.error("Internal server error", e);

    return internalServerError("Internal server error");
  }

  /**
   * * Crea una respuesta 500 Internal Server Error con mensaje personalizado.
   *
   * @param message mensaje de error.
   * @return {@link ResponseEntity} con {@link FailureResponse}.
   */
  public static ResponseEntity<FailureResponse> internalServerError(String message) {
    return error(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * * Crea una respuesta 501 Not Implemented.
   *
   * @return {@link ResponseEntity} con mensaje de operación no soportada.
   */
  public static ResponseEntity<FailureResponse> notImplemented() {
    return error("Unsupported operation", HttpStatus.NOT_IMPLEMENTED);
  }
}
