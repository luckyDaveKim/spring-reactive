package com.dave.springreactive.section14;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@Slf4j
@RestController
public class MyController {
  @GetMapping("/async")
  public String async() throws InterruptedException {
    log.info("async()");
    Thread.sleep(2000);
    return "hello";
  }

  @GetMapping("/callable")
  public Callable<String> callable() {
    log.info("callable()");
    return () -> {
      log.info("async");
      Thread.sleep(2000);
      return "hello";
    };
  }
}
