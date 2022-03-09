package com.dave.springreactive.section6;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * SubscribeOn 예제 (데이터 생성쪽이 느릴 때)
 * */
@Slf4j
public class Main {
  public static void main(String[] args) {
    Publisher<Integer> pub = getPub();

    // subscribeOn 사용 예
    Publisher<Integer> subOnPub = sub -> {
      ExecutorService es = Executors.newSingleThreadExecutor();
      es.execute(() -> pub.subscribe(sub));
    };

    Subscriber<Integer> subscriber = getSub();

    subOnPub.subscribe(subscriber);

    log.debug("Exit!!");
  }

  private static Publisher<Integer> getPub() {
    return sub -> {
      sub.onSubscribe(new Subscription() {
        @Override
        public void request(long n) {
          log.debug("request");
          sub.onNext(1);
          sub.onNext(2);
          sub.onNext(3);
          sub.onNext(4);
          sub.onComplete();
        }

        @Override
        public void cancel() {

        }
      });
    };
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
