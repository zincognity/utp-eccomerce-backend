package com.utepinos.utp_eccomerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@RestController
public class UtpEccomerceApplication {

  public static void main(String[] args) {
    SpringApplication.run(UtpEccomerceApplication.class, args);
  }

  @GetMapping("/hello")
  public String hello(@RequestParam(value = "name") String name) {
    return String.format("Hello %s!", name);
  }
}
