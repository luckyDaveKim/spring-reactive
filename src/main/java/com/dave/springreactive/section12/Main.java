package com.dave.springreactive.section12;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Slf4j
public class Main {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    FutureTask<String> f = new FutureTask<>(() -> {
      Thread.sleep(2000);
      log.info("Async");
      return "Hello";
    }) {
      @Override
      protected void done() {
        try {
          log.info(get());
        } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
        }
      }
    };

    ExecutorService es = Executors.newCachedThreadPool();
    es.execute(f);
    es.shutdown();

    log.info("isDone : {}", f.isDone());

    Thread.sleep(2500);
    log.info("Exit");

    log.info("isDone : {}", f.isDone());

    /*
     * isDone:F -> Async -> Hello -> Exit -> isDone:T
     * */
  }
}
