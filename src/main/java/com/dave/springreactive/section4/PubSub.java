package com.dave.springreactive.section4;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * pub -> [Data1] -> reducePub -> [Data2] -> logSub
 * */
@Slf4j
public class PubSub {
  public static void main(String[] args) {
    Publisher<Integer> pub = getPub(Stream.iterate(1, i -> i + 1).limit(10).collect(Collectors.toList()));
    Publisher<Integer> reducePub = getReducePub(pub, 0, Integer::sum);
    reducePub.subscribe(getLogSub());
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

  private static <T> Publisher<T> getReducePub(Publisher<T> pub, T init, BiFunction<T, T, T> biFun) {
    return sub -> pub.subscribe(new DelegateSub<>(sub) {
      T result = init;

      @Override
      public void onNext(T i) {
        result = biFun.apply(result, i);
      }

      @Override
      public void onComplete() {
        sub.onNext(result);
        sub.onComplete();
      }
    });
  }

  private static <T> Subscriber<T> getLogSub() {
    return new Subscriber<>() {
      @Override
      public void onSubscribe(Subscription sub) {
        sub.request(Long.MAX_VALUE);
      }

      @Override
      public void onNext(T i) {
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
