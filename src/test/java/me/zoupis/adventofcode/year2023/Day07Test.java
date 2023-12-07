package me.zoupis.adventofcode.year2023;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import me.zoupis.adventofcode.year2023.Day07.Type;

class Day07Test {

  @ParameterizedTest
  @MethodSource("identifyTypeData")
  void testIdentifyType(String hand, Type expected) {

    var actual = Day07.identifyType(hand);

    Assertions.assertEquals(expected, actual);
  }

  public static Stream<Arguments> identifyTypeData() {
    return Stream.of(
      Arguments.of("32T3K", Type.ONE_PAIR),
      Arguments.of("T55J5", Type.THREE_OF_A_KIND),
      Arguments.of("KK677", Type.TWO_PAIR),
      Arguments.of("KTJJT", Type.TWO_PAIR),
      Arguments.of("QQQJA", Type.THREE_OF_A_KIND),
      Arguments.of("AAAAA", Type.FIVE_OF_A_KIND),
      Arguments.of("AAAQQ", Type.FULL_HOUSE),
      Arguments.of("AAAAQ", Type.FOUR_OF_A_KIND),
      Arguments.of("23456", Type.HIGH_CARD)
    );
  }
}
