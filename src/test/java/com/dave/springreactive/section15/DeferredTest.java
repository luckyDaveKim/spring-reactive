package com.dave.springreactive.section15;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
class DeferredTest {
  @Test
  void loadTest() throws InterruptedException {
    final String url = "http://localhost:8080/deferred";

    ExecutorService es = Executors.newFixedThreadPool(100);

    RestTemplate template = new RestTemplate();

    AtomicInteger counter = new AtomicInteger(0);
    for (int i = 0; i < 100; i++) {
      es.execute(() -> {
        int cur = counter.addAndGet(1);
        log.info("Thread {}", cur);

        String result = template.getForObject(url, String.class);

        log.info("Elapsed {}; Result: {}", cur, result);
      });
    }

    es.shutdown();
    es.awaitTermination(100, TimeUnit.SECONDS);
  }
}