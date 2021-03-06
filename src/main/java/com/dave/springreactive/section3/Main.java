package com.dave.springreactive.section3;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Flow.*;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    ExecutorService es = Executors.newSingleThreadExecutor();

    Publisher<Integer> pub = getPub(es);
    Subscriber<Integer> sub = getSub();

    pub.subscribe(sub);

    es.awaitTermination(1, TimeUnit.SECONDS);
    es.shutdown();
  }

  private static Publisher<Integer> getPub(ExecutorService es) {
    return new Publisher<>() {
      @Override
      public void subscribe(Subscriber<? super Integer> subscriber) {
        Iterator<Integer> iter = Arrays.asList(1, 2, 3, 4, 5).iterator();

        subscriber.onSubscribe(new Subscription() {
          @Override
          public void request(long n) {
            es.execute(() -> {
              int i = 0;
              try {
                while (i++ < n) {
                  if (iter.hasNext()) {
                    subscriber.onNext(iter.next());
                  } else {
                    subscriber.onComplete();
                    break;
                  }
                }
              } catch (Exception e) {
                subscriber.onError(e);
              }
            });
          }

          @Override
          public void cancel() {

          }
        });
      }
    };
  }

  private static Subscriber<Integer> getSub() {
    return new Subscriber<>() {
      final int MAX_BUFFER_SIZE = 3;
      int bufferSize = MAX_BUFFER_SIZE;
      Subscription subscription;

      @Override
      public void onSubscribe(Subscription subscription) {
        System.out.println("Main.onSubscribe");

        this.subscription = subscription;

        // value 갯수만큼 받고싶다고 요청함
        this.subscription.request(MAX_BUFFER_SIZE);
      }

      @Override
      public void onNext(Integer item) {
        System.out.println("Main.onNext");
        System.out.println("item = " + item);

        if (--bufferSize <= 0) {
          bufferSize = MAX_BUFFER_SIZE;
          this.subscription.request(MAX_BUFFER_SIZE);
        }
      }

      @Override
      public void onError(Throwable throwable) {
        System.out.println("Main.onError");
      }

      @Override
      public void onComplete() {
        System.out.println("Main.onComplete");
      }
    };
  }
}
