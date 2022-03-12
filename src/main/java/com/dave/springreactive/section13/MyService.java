package com.dave.springreactive.section13;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyService {
  public String hello() throws InterruptedException {
    log.info("hello()");
    Thread.sleep(1000);
    return "Hello";
  }
}
