package com.utepinos.utp_ecommerce.api.shared.adapter.out.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utepinos.utp_ecommerce.api.shared.adapter.in.filter.JwtAuthenticationFilter;
import com.utepinos.utp_ecommerce.api.shared.adapter.in.response.FailureResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * * Clase de configuración para la seguridad de la aplicación.
 */
@Slf4j
@Configuration

@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter authenticationFilter;
  private final ObjectMapper objectMapper;
  private final AuthenticationProvider authenticationProvider;

  /**
   * * Configura el filtro de seguridad y la cadena de filtros de seguridad HTTP.
   *
   * @param http {@link HttpSecurity} HttpSecurity utilizado para configurar la
   *             seguridad HTTP.
   * @return {@link SecurityFilterChain} La cadena de filtros de seguridad
   *         configurada.
   * @throws Exception Si hay algún error durante la configuración.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .formLogin(FormLoginConfigurer::disable)
        .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
            .requestMatchers("/user/**").authenticated()
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/public/**").permitAll()
            .anyRequest().authenticated())
        .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(this::manageNoAuthorized)
            .accessDeniedHandler(this::manageNoAuthorized))
        .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(
            authenticationProvider)
        .addFilterBefore(
            authenticationFilter,
            UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  /**
   * Maneja los casos en los que una solicitud no está autorizada.
   *
   * @param request  {@link HttpServletRequest} La solicitud HTTP entrante.
   * @param response {@link HttpServletResponse} La respuesta HTTP saliente.
   * @param e        {@link RuntimeException} La excepción que provocó el rechazo
   *                 de la autorización.
   * @throws JsonProcessingException Si ocurre un error al procesar el objeto
   *                                 JSON.
   * @throws IOException             Si ocurre un error de entrada/salida al
   *                                 escribir la respuesta.
   */
  private void manageNoAuthorized(HttpServletRequest request, HttpServletResponse response, RuntimeException e)
      throws JsonProcessingException, IOException {
    response.setStatus(403);
    response.setContentType("application/json");

    response.getWriter().write(objectMapper.writeValueAsString(new FailureResponse("Acceso denegado")));
  }
}