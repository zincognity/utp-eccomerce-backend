package com.utepinos.utp_ecommerce.api.user.adapter.out.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class UserTableInitializer {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @PostConstruct
  public void init() {
    jdbcTemplate.execute("DROP TABLE IF EXISTS users");

    jdbcTemplate.execute("""
          CREATE TABLE users (
            id SERIAL PRIMARY KEY,
            name VARCHAR(40),
            email VARCHAR(60),
            phone VARCHAR(15),
            register_date TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
            password VARCHAR(80)
          )
        """);
  }
}
