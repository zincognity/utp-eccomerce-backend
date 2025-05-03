package com.utepinos.utp_ecommerce.api.user.adapter.out.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * * Inicializa la tabla usuarios y las relaciones de esta misma.
 */
@Component
public class UserTableInitializer {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @PostConstruct
  public void init() {

    /**
     * * Elimina la tabla users si es que existe, debido a que será create-drop en
     * * dev mode.
     */
    jdbcTemplate.execute("DROP TABLE IF EXISTS users");
    jdbcTemplate.execute("DROP TYPE IF EXISTS user_role");

    // * Crea el tipo y la tabla de users con sus parámetros.
    jdbcTemplate.execute("CREATE TYPE user_role AS ENUM ('MODERATOR', 'USER')");
    jdbcTemplate.execute("""
          CREATE TABLE users (
            id SERIAL PRIMARY KEY,
            name VARCHAR(40),
            email VARCHAR(60),
            phone VARCHAR(15),
            register_date TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
            role user_role DEFAULT 'USER',
            password VARCHAR(80)
          )
        """);
  }
}
