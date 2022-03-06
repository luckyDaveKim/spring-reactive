package com.dave.springreactive.section5;

import reactor.core.publisher.Flux;

public class ReactorExample {
  public static void main(String[] args) {
    Flux.create(e -> {
          e.next(1);
          e.next(2);
          e.next(3);
          e.complete();
        })
        .subscribe(System.out::println);
  }
}
