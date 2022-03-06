package com.dave.springreactive.section4;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class DelegateSub<T> implements Subscriber<T> {
  Subscriber<? super T> sub;

  public DelegateSub(Subscriber<? super T> sub) {
    this.sub = sub;
  }

  @Override
  public void onSubscribe(Subscription innerSub) {
    sub.onSubscribe(innerSub);
  }

  @Override
  public void onNext(T i) {
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
