package com.utepinos.utp_ecommerce.api.shared.domain.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.utepinos.utp_ecommerce.api.auth.core.domain.model.Jwt;
import com.utepinos.utp_ecommerce.api.user.domain.model.User;
import com.utepinos.utp_ecommerce.api.user.domain.model.enums.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;

/**
 * * Clase utilitaria para la generación y validación de jwt.
 */
@Service
public class JwtUtils {
  @Value("${spring.security.jwt.secret}")
  private String JWT_SECRET;
  @Value("${spring.security.jwt.expiration}")
  private Long JWT_EXPIRATION;

  /**
   * * Genera un jwt para el usuario proporcionado.
   * 
   * @param user {@link User} El usuario.
   * @return {@link Jwt} JWT generado.
   */
  public String generateToken(User user) {
    Date issueAt = new Date(System.currentTimeMillis());
    Date expiration = new Date(System.currentTimeMillis() + JWT_EXPIRATION);

    Map<String, Object> claims = generateClaims(user);

    SecretKey key = getKey();
    MacAlgorithm signatureAlgorithm = Jwts.SIG.HS256;

    return Jwts.builder()
        .header().type("JWT").and()
        .claims(claims)
        .issuedAt(issueAt)
        .expiration(expiration)
        .signWith(key, signatureAlgorithm)
        .compact();
  }

  /**
   * * Verifica si el token JWT proporcionado es válido para el usuario
   * * proporcionado.
   *
   * @param token {@link Jwt} El token JWT a validar.
   * @param user  {@link User} Los detalles del usuario para validar
   *              el token.
   * @return {@link Boolean} True si el token es válido, false en caso contrario.
   */
  public Boolean isTokenValid(String token, User user) {
    User username = getUserFromToken(token);
    Boolean isExpired = isTokenExpired(token);

    return username.getEmail().equals(user.getEmail()) && !isExpired;
  }

  /**
   * * Obtiene el nombre de usuario del jwt proporcionado.
   * 
   * @param jwt {@link Jwt} El token del que se extraerá el nombre de usuario.
   * @return {@link String} El nombre de usuario extraídos del token.
   */
  public User getUserFromToken(String jwt) {
    try {
      Claims claims = getAllClaims(jwt);

      Object userObject = claims.get("user");
      if (!(userObject instanceof Map)) {
        throw new IllegalArgumentException("Invalid user claims in token.");
      }
      @SuppressWarnings("unchecked")
      Map<String, Object> userClaims = (Map<String, Object>) userObject;

      Long userId = userClaims.containsKey("id") ? Long.parseLong(userClaims.get("id").toString()) : null;
      String userName = userClaims.containsKey("name") ? userClaims.get("name").toString() : null;
      String userEmail = userClaims.containsKey("email") ? userClaims.get("email").toString() : null;
      String userPhone = userClaims.containsKey("phone") ? userClaims.get("phone").toString() : null;
      String userRoleString = userClaims.containsKey("role") ? userClaims.get("role").toString() : null;

      if (userId == null || userName == null || userEmail == null || userRoleString == null)
        throw new IllegalArgumentException("Missing required claim values in token.");

      UserRole userRole;
      userRole = UserRole.valueOf(userRoleString);

      return User.builder()
          .id(userId)
          .name(userName)
          .email(userEmail)
          .phone(userPhone)
          .role(userRole)
          .build();
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Error processing token: " + e.getMessage(), e);
    } catch (Exception e) {
      throw new RuntimeException("Unexpected error extracting user from token", e);
    }
  }

  /**
   * * Obtiene la fecha de expiración del jwt proporcionado.
   * 
   * @param jwt {@link Jwt} El jwt.
   * @return {@link Date} La fecha de expiración del token.
   */
  private Date getExpirationFromToken(String jwt) {
    return getClaim(jwt, Claims::getExpiration);
  }

  /**
   * * Obtiene la clave de firma para la generación y validación de jwt.
   * 
   * @return {@link SecretKey} La clave de firma.
   */
  private SecretKey getKey() {
    return Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
  }

  /**
   * * Genera los claims adicionales para el jwt a partir de los datos del
   * * usuario.
   * 
   * @param user {@link User} El usuario para el que se general los claims.
   * @return {@link Map} Un mapa que contiene los claims adicionales.
   */
  private Map<String, Object> generateClaims(User user) {
    Map<String, Object> claims = new HashMap<>();

    Map<String, Object> publicUser = new HashMap<>();
    publicUser.put("id", user.getId());
    publicUser.put("name", user.getName());
    publicUser.put("email", user.getEmail());
    publicUser.put("phone", user.getPhone());
    publicUser.put("role", user.getRole().name());

    claims.put("user", publicUser);

    return claims;
  }

  /**
   * * Obtiene un claim específico del jwt.
   *
   * @param token          {@link Jwt} El jwt del que se extraerá el claim.
   * @param claimsResolver {@link Function} El resolvedor de claims que se usará
   *                       para obtener el claim específico.
   * @return El valor del claim especificado.
   */
  private <T> T getClaim(String token, Function<Claims, T> resolver) {
    return resolver.apply(getAllClaims(token));
  }

  private Claims getAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  /**
   * * Verifica si el token JWT proporcionado ha expirado.
   *
   * @param token {@link Jwt} El token JWT a verificar.
   * @return {@link Boolean} True si el token ha expirado, false en caso
   *         contrario.
   */
  private Boolean isTokenExpired(String token) {
    return getExpirationFromToken(token).before(new Date());
  }
}
