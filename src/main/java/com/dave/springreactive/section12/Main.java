package com.dave.springreactive.section12;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
public class Main {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    CallbackFutureTask f = new CallbackFutureTask(() -> {
      Thread.sleep(2000);
      if (true) throw new RuntimeException("Async Error!");
      log.info("Async");
      return "Hello";
    },
        result -> log.info("Result : {}", result),
        e -> log.error("Error : {}", e.getMessage()));

    ExecutorService es = Executors.newCachedThreadPool();
    es.execute(f);
    es.shutdown();

    log.info("isDone : {}", f.isDone());

    Thread.sleep(2500);
    log.info("Exit");

    log.info("isDone : {}", f.isDone());

    /*
     * isDone:F -> Error -> Exit -> isDone:T
     * */
  }

  public static class CallbackFutureTask extends FutureTask<String> {
    SuccessCallback successCallback;
    ExceptionCallback exceptionCallback;

    public CallbackFutureTask(Callable<String> callable, SuccessCallback successCallback, ExceptionCallback exceptionCallback) {
      super(callable);
      this.successCallback = Objects.requireNonNull(successCallback);
      this.exceptionCallback = Objects.requireNonNull(exceptionCallback);
    }

    @Override
    protected void done() {
      try {
        successCallback.onSuccess(get());
      } catch (ExecutionException e) {
        this.exceptionCallback.onError(e.getCause());
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  interface SuccessCallback {
    void onSuccess(String result);
  }

  interface ExceptionCallback {
    void onError(Throwable t);
  }
}
