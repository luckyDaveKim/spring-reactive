package com.dave.springreactive.section1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * for-in은 List가 아닌 Iterable이면 동작한다.
 * List 또한 Iterable을 구현했기에 for-in을 사용할 수 있다.
 * */
class MainTest {

  @Test
  void listForInTest() {
    // given
    List<Integer> controlGroup = Arrays.asList(1, 2, 3, 4, 5);
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

    // when
    List<Integer> targetGroup = new LinkedList<>();
    for (int i : list) {
      targetGroup.add(i);
    }

    // then
    assertEquals(controlGroup, targetGroup);
  }

  @Test
  void iterableForInTest() {
    // given
    List<Integer> controlGroup = Arrays.asList(1, 2, 3, 4, 5);
    Iterable<Integer> iter = () -> new Iterator<>() {
      final static int MAX = 5;
      int i = 0;

      @Override
      public boolean hasNext() {
        return i < MAX;
      }

      @Override
      public Integer next() {
        return ++i;
      }
    };

    // when
    List<Integer> targetGroup = new LinkedList<>();
    for (int i : iter) {
      targetGroup.add(i);
    }

    // then
    assertEquals(controlGroup, targetGroup);
  }

}