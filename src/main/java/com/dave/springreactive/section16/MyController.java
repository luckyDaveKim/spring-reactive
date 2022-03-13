package com.dave.springreactive.section16;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.concurrent.Executors;

@Slf4j
@RestController
public class MyController {
  @GetMapping("/emitter")
  public ResponseBodyEmitter emitter() {
    ResponseBodyEmitter emitter = new ResponseBodyEmitter();

    Executors.newSingleThreadExecutor().submit(() -> {
      try {
        for (int i = 1; i <= 50; i++) {
          emitter.send(String.format("<p>Stream %d</p>", i));
          Thread.sleep(100);
        }
        emitter.complete();
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    });

    return emitter;
  }
}
