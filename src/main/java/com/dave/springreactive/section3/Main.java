package com.dave.springreactive.section3;

import java.util.Arrays;
import java.util.Iterator;

import static java.util.concurrent.Flow.*;

public class Main {
  public static void main(String[] args) {
    Publisher<Integer> pub = getPub();
    Subscriber<Integer> sub = getSub();

    pub.subscribe(sub);
  }

  private static Publisher<Integer> getPub() {
    return new Publisher<>() {
      @Override
      public void subscribe(Subscriber<? super Integer> subscriber) {
        Iterator<Integer> iter = Arrays.asList(1, 2, 3, 4, 5).iterator();

        subscriber.onSubscribe(new Subscription() {
          @Override
          public void request(long n) {
            while (n-- > 0) {
              if (iter.hasNext()) {
                subscriber.onNext(iter.next());
              } else {
                subscriber.onComplete();
                break;
              }
            }
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
      Subscription subscription;

      @Override
      public void onSubscribe(Subscription subscription) {
        System.out.println("Main.onSubscribe");

        this.subscription = subscription;

        // value 갯수만큼 받고싶다고 요청함
        this.subscription.request(1);
      }

      @Override
      public void onNext(Integer item) {
        System.out.println("Main.onNext");
        System.out.println("item = " + item);

        this.subscription.request(1);
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
