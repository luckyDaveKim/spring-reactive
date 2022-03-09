package com.dave.springreactive.section10;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {
  public static void main(String[] args) throws InterruptedException {
    Flux.interval(Duration.ofMillis(400))
        .subscribe(i -> log.debug("onNext : {}", i));

    TimeUnit.SECONDS.sleep(2);
  }
}
