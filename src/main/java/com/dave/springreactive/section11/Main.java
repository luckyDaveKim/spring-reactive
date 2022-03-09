package com.dave.springreactive.section11;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/*
 * Operator
 * 데이터 제어
 * 스케쥴링
 * publisher 컨트롤
 * */
@Slf4j
public class Main {
  public static void main(String[] args) {
    Publisher<Integer> pub = getPub();

    Publisher<Integer> takePub = sub -> pub.subscribe(new Subscriber<>() {
      final int MAX_COUNT = 5;
      Subscription subscription;
      int count = 0;

      @Override
      public void onSubscribe(Subscription s) {
        this.subscription = s;
        sub.onSubscribe(s);
      }

      @Override
      public void onNext(Integer i) {
        if (++count >= MAX_COUNT) {
          subscription.cancel();
        }

        sub.onNext(i);
      }

      @Override
      public void onError(Throwable t) {
        sub.onError(t);
      }

      @Override
      public void onComplete() {
        sub.onComplete();
      }
    });

    Subscriber<Integer> subscriber = getSub();

    takePub.subscribe(subscriber);

    log.debug("Exit!!");
  }

  private static Publisher<Integer> getPub() {
    return sub -> sub.onSubscribe(new Subscription() {
      int i = 0;
      boolean cancelled = false;

      @Override
      public void request(long n) {
        log.debug("request");
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
          if (cancelled) {
            exec.shutdown();
            return;
          }

          sub.onNext(i++);
        }, 0, 300, TimeUnit.MILLISECONDS);
      }

      @Override
      public void cancel() {
        cancelled = true;
      }
    });
  }

  private static Subscriber<Integer> getSub() {
    return new Subscriber<>() {
      @Override
      public void onSubscribe(Subscription subscription) {
        log.debug("onSubscribe");

        // 최대한 받고싶다고 요청함
        subscription.request(Long.MAX_VALUE);
      }

      @Override
      public void onNext(Integer item) {
        log.debug("onNext = {}", item);
      }

      @Override
      public void onError(Throwable throwable) {
        log.debug("onError");
      }

      @Override
      public void onComplete() {
        log.debug("onComplete");
      }
    };
  }
}
