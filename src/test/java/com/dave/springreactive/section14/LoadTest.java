package com.dave.springreactive.section14;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
class LoadTest {
  private void loadTest(final String url) throws InterruptedException {
    ExecutorService es = Executors.newFixedThreadPool(100);

    RestTemplate template = new RestTemplate();

    StopWatch mainStopWatch = new StopWatch();
    mainStopWatch.start();

    AtomicInteger counter = new AtomicInteger(0);
    for (int i = 0; i < 100; i++) {
      es.execute(() -> {
        int cur = counter.addAndGet(1);
        log.info("Thread {}", cur);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        template.getForObject(url, String.class);

        stopWatch.stop();
        log.info("Elapsed {} : {}", cur, stopWatch.getTotalTimeSeconds());
      });
    }

    es.shutdown();
    es.awaitTermination(100, TimeUnit.SECONDS);

    mainStopWatch.stop();
    log.info("Total: {}", mainStopWatch.getTotalTimeSeconds());
  }

  @Test
  void asyncLoadTest() throws InterruptedException {
    final String url = "http://localhost:8080/async";
    loadTest(url);
  }

  @Test
  void callableLoadTest() throws InterruptedException {
    final String url = "http://localhost:8080/callable";
    loadTest(url);
  }
}