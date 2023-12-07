package me.zoupis.adventofcode.year2023;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day01Test {

  @ParameterizedTest
  @MethodSource("generateData")
  void testGetDigits(String line, List<Integer> expected) {
    List<Integer> actual = Day01.getDigits(line);

    Assertions.assertEquals(expected.size(), actual.size());
    Assertions.assertEquals(expected, actual);
  }

  static Stream<Arguments> generateData() {
    return Stream.of(
      Arguments.of("1abc2", Arrays.asList(1, 2)),
      Arguments.of("pqr3stu8vwx", Arrays.asList(3, 8)),
      Arguments.of("a1b2c3d4e5f", Arrays.asList(1, 2, 3, 4, 5)),
      Arguments.of("treb7uchet", List.of(7))
    );
  }
}
