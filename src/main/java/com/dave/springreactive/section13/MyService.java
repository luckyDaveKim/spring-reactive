package com.dave.springreactive.section13;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Component
public class MyService {
  @Async
  public ListenableFuture<String> hello() throws InterruptedException {
    log.info("hello()");
    Thread.sleep(1000);
    return new AsyncResult<>("Hello");
  }
}
