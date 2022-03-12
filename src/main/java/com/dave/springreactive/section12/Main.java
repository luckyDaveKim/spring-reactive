package com.dave.springreactive.section12;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
public class Main {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    CallbackFutureTask f = new CallbackFutureTask(() -> {
      Thread.sleep(2000);
      log.info("Async");
      return "Hello";
    }, log::info);

    ExecutorService es = Executors.newCachedThreadPool();
    es.execute(f);
    es.shutdown();

    log.info("isDone : {}", f.isDone());

    Thread.sleep(2500);
    log.info("Exit");

    log.info("isDone : {}", f.isDone());

    /*
     * isDone:F -> Async -> Hello -> Exit -> isDone:T
     * */
  }

  public static class CallbackFutureTask extends FutureTask<String> {
    SuccessCallback successCallback;

    public CallbackFutureTask(Callable<String> callable, SuccessCallback successCallback) {
      super(callable);
      this.successCallback = Objects.requireNonNull(successCallback);
    }

    @Override
    protected void done() {
      try {
        successCallback.onSuccess(get());
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
  }

  interface SuccessCallback {
    void onSuccess(String result);
  }
}
