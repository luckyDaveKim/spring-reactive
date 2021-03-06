package com.dave.springreactive.section2;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("deprecation")
public class Main {

  /*
   * = Observer
   * = Subscriber
   * */
  public static void main(String[] args) {
    Observer observer = new Observer() {
      @Override
      public void update(Observable o, Object arg) {
        System.out.printf("[%s] %s \n", Thread.currentThread().getName(), arg);
      }
    };

    IntObservable intObservable = new IntObservable();
    intObservable.addObserver(observer);

    ExecutorService es = Executors.newSingleThreadExecutor();
    es.execute(intObservable);

    System.out.printf("[%s] EXIT \n", Thread.currentThread().getName());
    es.shutdown();
  }

  /*
   * = Observable
   * = Subject
   * = Publisher
   * */
  static class IntObservable extends Observable implements Runnable {

    @Override
    public void run() {
      for (int i = 0; i < 10; i++) {
        setChanged();
        notifyObservers(i);
      }
    }
  }
}