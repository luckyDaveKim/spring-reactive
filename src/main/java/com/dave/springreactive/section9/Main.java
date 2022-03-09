package com.dave.springreactive.section9;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
* publishOn, subscribeOn on Flux
* */
@Slf4j
public class Main {
  public static void main(String[] args) {
    Flux.range(1, 3)
        .publishOn(Schedulers.newSingle("pub"))
        .log()
        .subscribeOn(Schedulers.newSingle("sub"))
        .subscribe(System.out::println);

    System.out.println("Exit");
  }
}
