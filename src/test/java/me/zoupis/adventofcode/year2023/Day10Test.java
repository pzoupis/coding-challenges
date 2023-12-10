package me.zoupis.adventofcode.year2023;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import me.zoupis.adventofcode.year2023.Day10.Point;

public class Day10Test {

  @ParameterizedTest
  @MethodSource(value = "generateGetNextPositionData")
  void testGetNextPosition(String symbol, Point previousPoint, Point currentPoint, Point expected) {
    Point actual = Day10.getNextPosition(symbol, previousPoint, currentPoint);

    Assertions.assertEquals(expected, actual);
  }

  public static Stream<Arguments> generateGetNextPositionData() {
    return Stream.of(
      Arguments.of("|", new Point(1,1), new Point(1,2), new Point(1,3)),
      Arguments.of("|", new Point(1,3), new Point(1,2), new Point(1,1)),
      Arguments.of("-", new Point(1,1), new Point(2,1), new Point(3,1)),
      Arguments.of("-", new Point(3,1), new Point(2,1), new Point(1,1)),
      Arguments.of("L", new Point(1,2), new Point(1,3), new Point(2,3)),
      Arguments.of("L", new Point(2,3), new Point(1,3), new Point(1,2)),
      Arguments.of("J", new Point(2,3), new Point(3,3), new Point(3,2)),
      Arguments.of("J", new Point(3,2), new Point(3,3), new Point(2,3)),
      Arguments.of("7", new Point(2,1), new Point(3,1), new Point(3,2)),
      Arguments.of("7", new Point(3,2), new Point(3,1), new Point(2,1)),
//      Arguments.of("F", new Point(1,1), new Point(0,0), new Point(-1,-1)),
//      Arguments.of("F", new Point(1,1), new Point(0,0), new Point(-1,-1)),
      Arguments.of("S", new Point(1,1), new Point(0,0), new Point(-1,-1)),
      Arguments.of(".", new Point(1,1), new Point(0,0), new Point(-1,-1)));
  }

  @ParameterizedTest
  @MethodSource(value = "generateIsFarthestPointData")
  void testIsFarthestPoint(Point first, Point second, boolean expected) {
    boolean actual = Day10.isFarthestPoint(List.of(first, second));

    Assertions.assertEquals(expected, actual);
  }

  public static Stream<Arguments> generateIsFarthestPointData() {
    return Stream.of(
      Arguments.of(new Point(5, 4), new Point(5, 4), true),
      Arguments.of(new Point(5, 5), new Point(5, 4), false)
    );
  }
}
