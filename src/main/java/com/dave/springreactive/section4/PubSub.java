package com.dave.springreactive.section4;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * pub -> [Data1] -> mapPub -> [Data2] -> logSub
 * */
@Slf4j
public class PubSub {
  public static void main(String[] args) {
    Publisher<Integer> pub = getPub(Stream.iterate(1, i -> i + 1).limit(10).collect(Collectors.toList()));
    Publisher<Integer> mapPub = mapPub(pub, i -> i * 10);
    mapPub.subscribe(getLogSub());
  }

  private static Publisher<Integer> getPub(final List<Integer> iter) {
    return sub -> sub.onSubscribe(new Subscription() {
      @Override
      public void request(long n) {
        try {
          iter.forEach(sub::onNext);
          sub.onComplete();
        } catch (Throwable t) {
          sub.onError(t);
        }
      }

      @Override
      public void cancel() {

      }
    });
  }

  private static Publisher<Integer> mapPub(Publisher<Integer> pub, Function<Integer, Integer> fun) {
    return sub -> pub.subscribe(new Subscriber<>() {
      @Override
      public void onSubscribe(Subscription innerSub) {
        sub.onSubscribe(innerSub);
      }

      @Override
      public void onNext(Integer i) {
        sub.onNext(fun.apply(i));
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
  }

  private static Subscriber<Integer> getLogSub() {
    return new Subscriber<>() {
      @Override
      public void onSubscribe(Subscription sub) {
        sub.request(Long.MAX_VALUE);
      }

      @Override
      public void onNext(Integer i) {
        log.debug("onNext:{}", i);
      }

      @Override
      public void onError(Throwable t) {
        log.error("onError:{}", t.toString());
      }

      @Override
      public void onComplete() {
        log.debug("onComplete");
      }
    };
  }
}
