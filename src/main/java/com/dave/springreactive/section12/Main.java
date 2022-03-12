package com.dave.springreactive.section12;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class Main {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService es = Executors.newCachedThreadPool();

    // execute는 runnable 인터페이스를 받는다.
    // submit은 runnable, callable 인터페이스를 받는다. callable은 리턴값이 존재하며, 예외도 던질 수 있다.
    Future<String> f = es.submit(() -> {
      Thread.sleep(2000);
      log.info("Async");
      return "Hello";
    });

    log.info("isDone : {}", f.isDone());

    Thread.sleep(2500);
    log.info("Exit");

    log.info("isDone : {}", f.isDone());

    log.info("Future : {}", f.get());

    /*
     * isDone:F -> Async -> Exit -> isDone:T -> Future
     * */
  }
}
