package com.utepinos.utp_ecommerce.api.shared.domain.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.utepinos.utp_ecommerce.api.shared.domain.util.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

/**
 * * Clase de configuración para la seguridad de la aplicación.
 */
@Configuration

@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter authenticationFilter;

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
        .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
            .requestMatchers("/user/**").authenticated()
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/public/**").permitAll()
            .anyRequest().authenticated())
        .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(
            authenticationProvider)
        .addFilterBefore(
            authenticationFilter,
            UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}