package com.utepinos.utp_ecommerce.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@RestController
public class UtpecommerceApplication {

  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.load();
    System.setProperty("DB_HOSTNAME", dotenv.get("DB_HOSTNAME"));
    System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
    System.setProperty("DB_DATABASE", dotenv.get("DB_DATABASE"));
    System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
    System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

    SpringApplication.run(UtpecommerceApplication.class, args);
  }
}
