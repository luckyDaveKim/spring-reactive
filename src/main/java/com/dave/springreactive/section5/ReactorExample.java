package com.dave.springreactive.section5;

import reactor.core.publisher.Flux;

public class ReactorExample {
  public static void main(String[] args) {
    Flux.<Integer>create(e -> {
          e.next(1);
          e.next(2);
          e.next(3);
          e.complete();
        })
        .log()
        .map(i -> i * 10)
        .reduce(0, Integer::sum)
        .log()
        .subscribe(System.out::println);
  }
}
