package me.zoupis.adventofcode.year2023;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.zoupis.common.InputHandler;
import me.zoupis.common.Point;

public class Day11 {
  private static final Logger LOGGER = LogManager.getLogger(Day11.class);
  private static final InputHandler INPUT_HANDLER = InputHandler.getInstance();

  private static final int EXPANSION_RATE = 10;

  public static void main(String[] args) {
    var input = INPUT_HANDLER.readInputFile("adventofcode/year2023/day11.input");

    LOGGER.info(part1(input));
  }

  private static long part1(List<String> input) {
    input = expandUniverse(input);
    var galaxies = getAllGalaxies(input);
    return calculateShortestPathBetweenEveryPairOfGalaxies(galaxies);
  }

  public static long calculateShortestPathBetweenEveryPairOfGalaxies(List<Point> galaxies) {
    long sum = 0;
    for (int i = 0; i < galaxies.size(); i++) {
      for (int j = i + 1; j < galaxies.size(); j++) {
        sum += calculateManhattanDistance(galaxies.get(i), galaxies.get(j));
      }
    }
    return sum;
  }

  public static List<Point> getAllGalaxies(List<String> input) {
    List<Point> galaxies = new ArrayList<>();
    for (int i = 0; i < input.size(); i++) {
      var indexes = getAllIndexesOf("#", input.get(i));
      int finalI = i;
      var points = indexes.stream()
        .map(x -> new Point(x, finalI))
        .toList();
      galaxies.addAll(points);
    }
    return galaxies;
  }

  public static List<Integer> getAllIndexesOf(String character, String line) {
    List<Integer> indexes = new ArrayList<>();
    int index = line.indexOf(character);
    while (index >= 0) {
      indexes.add(index);
      index = line.indexOf(character, index + 1);
    }
    return indexes;
  }

  public static List<String> expandUniverse(List<String> input) {
    expandUniverseVertically(input);
    return expandUniverseHorizontally(input);
  }

  public static void expandUniverseVertically(List<String> input) {
    List<String> expansion = new ArrayList<>();
    for (int i = 0; i < EXPANSION_RATE; i++) {
      expansion.add(".".repeat(input.get(0).length()));
    }

    List<Integer> indexes = new ArrayList<>();
    for (int i = 0; i < input.size(); i++) {
      if (input.get(i).chars().allMatch(c -> c == '.')) {
        indexes.add(i + indexes.size() + (EXPANSION_RATE * indexes.size()));
      }
    }
    for (Integer index : indexes) {
      input.addAll(index, expansion);
    }
  }

  public static List<String> expandUniverseHorizontally(List<String> input) {
    StringBuilder expansion = new StringBuilder();
    expansion.append(".".repeat(EXPANSION_RATE));

    List<Integer> indexes = new ArrayList<>();
    for (int i = 0; i < input.get(0).length(); i++) {
      final int temp = i;
      if (input.stream().allMatch(s -> s.charAt(temp) == '.')) {
        indexes.add(i + indexes.size() + (EXPANSION_RATE * indexes.size()));
      }
    }
    List<String> newInput = new ArrayList<>();
    for (String line : input) {
      StringBuilder stringBuilder = new StringBuilder(line);
      for (int index : indexes) {
        stringBuilder.insert(index, expansion);
      }
      newInput.add(stringBuilder.toString());
    }
    return newInput;
  }

  public static int calculateManhattanDistance(Point a, Point b) {
    return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
  }
}
