package com.utepinos.utp_ecommerce.api.shared.adapter.in.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.utepinos.utp_ecommerce.api.shared.domain.util.JwtUtils;
import com.utepinos.utp_ecommerce.api.user.domain.model.User;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * * Filtro de autenticación JWT que verifica la validez del token en cada
 * * solicitud.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtUtils jwtUtil;
  private final UserDetailsService userDetailsService;

  /**
   * * Realiza la lógica de filtrado para autenticar las solicitudes mediante
   * * tokens JWT.
   *
   * @param request     {@link HttpServletRequest} La solicitud HTTP entrante.
   * @param response    {@link HttpServletResponse} La respuesta HTTP saliente.
   * @param filterChain {@link FilterChain} La cadena de filtros para invocar
   *                    después de la autenticación.
   * @throws {@link ServletException} Si ocurre un error en la servlet.
   * @throws {@link IOException} Si ocurre un error de entrada/salida.
   */
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    try {
      if (isAuthRequest(request)) {
        filterChain.doFilter(request, response);
        return;
      }

      String token = getTokenFromRequest(request);

      if (token == null) {
        filterChain.doFilter(request, response);
        return;
      }

      User user = jwtUtil.getUserFromToken(token);

      if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = getUserDetails(user.getUsername());

        if (jwtUtil.isTokenValid(token, user))
          setAuthentication(request, userDetails);

      }

      filterChain.doFilter(request, response);
    } catch (MalformedJwtException e) {
      handleErrorToken(response, "Malformed JWT: " + e.getMessage());
    } catch (JwtException e) {
      handleErrorToken(response, "JWT Exception: " + e.getMessage());
    }
  }

  /**
   * * Verifica si la solicitud es una solicitud de autenticación.
   *
   * @param request {@link HttpServletRequest} La solicitud HTTP entrante.
   * @return {@link Boolean} True si la solicitud es para autenticación, false en
   *         caso contrario.
   */
  private Boolean isAuthRequest(HttpServletRequest request) {
    return request.getServletPath().contains("/auth");
  }

  /**
   * * Obtiene el token JWT de la solicitud HTTP.
   *
   * @param request {@link HttpServletRequest} La solicitud HTTP entrante.
   * @return {@link String} El token JWT si está presente en la solicitud, null si
   *         no lo está.
   */
  private String getTokenFromRequest(HttpServletRequest request) {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (header != null && header.startsWith("Bearer ")) {
      return header.replace("Bearer ", "");
    }

    return null;
  }

  /**
   * * Obtiene los detalles del usuario a partir del nombre de usuario.
   *
   * @param username {@link String} El nombre de usuario del usuario.
   * @return {@link UserDetails} Los detalles del usuario.
   */
  private UserDetails getUserDetails(String username) {
    return userDetailsService.loadUserByUsername(username);
  }

  /**
   * * Establece la autenticación en el contexto de seguridad de Spring Security.
   *
   * @param request     {@link HttpServletRequest} La solicitud HTTP entrante.
   * @param userDetails {@link UserDetails} Los detalles del usuario autenticado.
   */
  private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails,
        null,
        userDetails.getAuthorities());
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  }

  /**
   * * Maneja un error relacionado con el token JWT y responde con un mensaje de
   * * error.
   *
   * @param response {@link HttpServletResponse} La respuesta HTTP saliente.
   * @param error    {@link String} El mensaje de error a incluir en la respuesta.
   * @throws {@link IOException} Si ocurre un error de entrada/salida.
   */
  private void handleErrorToken(HttpServletResponse response, String error) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.getWriter().write("{\"error\": \"" + error + "\"}");
  }
}