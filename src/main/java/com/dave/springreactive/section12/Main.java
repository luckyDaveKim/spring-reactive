package com.dave.springreactive.section12;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Main {
  public static void main(String[] args) {
    ExecutorService es = Executors.newCachedThreadPool();

    // execute는 runnable 인터페이스를 받는다.
    es.execute(() -> {
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        log.error(e.getMessage());
      }

      log.info("Async");
    });

    log.info("Exit");
  }
}
