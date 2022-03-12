package com.dave.springreactive.section13;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyController {
  private final MyService myService;

  public MyController(MyService myService) {
    this.myService = myService;
  }

  @Bean
  public ApplicationRunner run() {
    return args -> {
      log.info("run()");
      String result = myService.hello();
      log.info("Result : {}", result);
      log.info("Exit");
    };
  }
}
