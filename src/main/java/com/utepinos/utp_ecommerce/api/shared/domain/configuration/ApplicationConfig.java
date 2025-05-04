package com.utepinos.utp_ecommerce.api.shared.domain.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.utepinos.utp_ecommerce.api.user.application.in.FindUserPort;

import lombok.RequiredArgsConstructor;

/**
 * * Configuración de la aplicación que define los beans necesarios para la
 * * autenticación.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
  private final FindUserPort findUserPort;

  /**
   * * Bean que proporciona un servicio para cargar detalles de usuario basados en
   * * el nombre de usuario.
   *
   * @return {@link UserDetailsService} Implementación de UserDetailsService que
   *         busca usuarios por su correo
   *         electrónico.
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> findUserPort.getByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
  }

  /**
   * * Bean que proporciona un codificador de passwords para encriptar y comparar
   * * passwords.
   *
   * @return {@link BCryptPasswordEncoder} El encoder que utiliza el algoritmo de
   *         hashing bcrypt.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * * Bean que proporciona un proveedor de autenticación que usa los servicios
   * * de usuario y codificador de contraseñas.
   *
   * @return {@link DaoAuthenticationProvider} Configuración con
   *         UserDetailsService y PasswordEncoder.
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService());
    provider.setPasswordEncoder(passwordEncoder());

    return provider;
  }

  /**
   * * Bean que proporciona el administrador de autenticación para la config
   * * de Spring Security.
   *
   * @param configuration {@link AuthenticationConfiguration} Configuración de
   *                      autenticación para obtener el
   *                      administrador de autenticación.
   * @return {@link AuthenticationManager} AuthenticationManager obtenido de la
   *         configuración de autenticación.
   * @throws Exception Si hay un error al obtener el administrador de
   *                   autenticación.
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }
}
