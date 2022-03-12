package com.dave.springreactive.section13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Main {
  public static void main(String[] args) {
    ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);
  }
}
