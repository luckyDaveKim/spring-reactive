package com.dave.springreactive.section13;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Slf4j
@Component
public class MyController {
  private final MyService myService;

  public MyController(MyService myService) {
    this.myService = myService;
  }

  @Bean
  public ApplicationRunner run() {
    /*
     * run() -> isDone:F -> hello() -> Result -> Exit
     * */
    return args -> {
      log.info("run()");
      Future<String> f = myService.hello();
      log.info("isDone : {}", f.isDone());
      log.info("Result : {}", f.get());
      log.info("Exit");
    };
  }
}
