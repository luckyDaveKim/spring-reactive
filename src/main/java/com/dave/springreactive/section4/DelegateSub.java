package com.dave.springreactive.section4;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class DelegateSub implements Subscriber<Integer> {
  Subscriber<? super Integer> sub;

  public DelegateSub(Subscriber<? super Integer> sub) {
    this.sub = sub;
  }

  @Override
  public void onSubscribe(Subscription innerSub) {
    sub.onSubscribe(innerSub);
  }

  @Override
  public void onNext(Integer i) {
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
}
