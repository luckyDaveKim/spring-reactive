package com.dave.springreactive.section13;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

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
     * run() -> isDone:F -> Exit -> hello() -> Result
     * */
    return args -> {
      log.info("run()");
      ListenableFuture<String> f = myService.hello();
      f.addCallback(result -> log.info("Result : {}", result), e -> log.error(e.getMessage()));
      log.info("isDone : {}", f.isDone());
      log.info("Exit");
    };
  }
}
