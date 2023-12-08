package me.zoupis.adventofcode.year2023;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day02Test {

  @Test
  void testGetGameId() {
    String line = "Game 11: 7 green, 4 blue, 14 red; 7 red, 8 green; 6 blue, 6 red; 5 blue, 10 red, 11 green; 12 red, 2 green";
    int expected = 11;
    int actual = Day02.getGameId(line);
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void testGetSets() {
    String line = "Game 11: 7 green, 4 blue, 14 red; 7 red, 8 green; 6 blue, 6 red; 5 blue, 10 red, 11 green; 12 red, 2 green";
    List<String> expected = List.of(
      "7 green, 4 blue, 14 red",
      "7 red, 8 green",
      "6 blue, 6 red",
      "5 blue, 10 red, 11 green",
      "12 red, 2 green"
    );
    List<String> actual = Day02.getSets(line);
    Assertions.assertLinesMatch(expected, actual);
  }

  @Test
  void testGetCubes() {
    String set = "7 green, 4 blue, 14 red";
    List<String> expected = List.of(
      "7 green",
      "4 blue",
      "14 red"
    );
    List<String> actual = Day02.getCubes(set);
    Assertions.assertLinesMatch(expected, actual);
  }
}
