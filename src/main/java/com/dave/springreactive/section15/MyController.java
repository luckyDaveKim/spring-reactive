package com.dave.springreactive.section15;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Slf4j
@RestController
public class MyController {
  Queue<DeferredResult<String>> queue = new ConcurrentLinkedDeque<>();

  @GetMapping("/deferred")
  public DeferredResult<String> deferred() {
    log.info("deferred()");
    DeferredResult<String> deferred = new DeferredResult<>(100000L);
    queue.add(deferred);
    return deferred;
  }

  @GetMapping("/deferred/count")
  public int countDeferred() {
    return queue.size();
  }

  @GetMapping("/deferred/event")
  public String deferredEvent(String msg) {
    for (DeferredResult<String> deferred : queue) {
      deferred.setResult(String.format("Hello %s", msg));
      queue.remove(deferred);
    }
    return "OK";
  }
}
