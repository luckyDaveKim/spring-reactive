package com.dave.springreactive.section13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@SpringBootApplication
public class Main {
  public static void main(String[] args) {
    ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);
  }

  @Bean
  ThreadPoolTaskExecutor threadPool() {
    ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
    threadPool.setCorePoolSize(10);
    threadPool.setMaxPoolSize(100);
    threadPool.setQueueCapacity(200);
    threadPool.initialize();
    return threadPool;
  }
}
