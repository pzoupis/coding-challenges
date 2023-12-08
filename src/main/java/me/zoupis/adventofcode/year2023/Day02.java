package me.zoupis.adventofcode.year2023;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.zoupis.common.InputHandler;

/**
 * Advent of code 2023 <a href="https://adventofcode.com/2023/day/2">Day 2</a>
 */
public class Day02 {
  private static final Logger LOGGER = LogManager.getLogger(Day02.class);
  private static final InputHandler INPUT_HANDLER = InputHandler.getInstance();

  private static final int MAX_RED_CUBES = 12;
  private static final int MAX_GREEN_CUBES = 13;
  private static final int MAX_BLUE_CUBES = 14;

  public static void main(String[] args) {
    List<String> input = INPUT_HANDLER.readInputFile("adventofcode/year2023/day02.input");

    LOGGER.info(part1(input));
  }

  private static int part1(List<String> input) {
    int sumOfIds = 0;
    for (String line : input) {
      sumOfIds += getValidGameId(line);
    }
    return sumOfIds;
  }

  public static int getValidGameId(String line) {
    List<String> sets = getSets(line);
    boolean gameIsValid = true;

    for (String set : sets) {
      int red = 0;
      int green = 0;
      int blue = 0;
      List<String> cubes = getCubes(set);
      for (String cube : cubes) {
        var amountAndColor = cube.split(" ");
        if (amountAndColor[1].equals("red")) red += Integer.parseInt(amountAndColor[0]);
        else if (amountAndColor[1].equals("green")) green += Integer.parseInt(amountAndColor[0]);
        else blue += Integer.parseInt(amountAndColor[0]);
      }

      if (!isValidSet(red, green, blue)) {
        gameIsValid = false;
      }
    }

    if (gameIsValid) {
      return getGameId(line);
    }
    return 0;
  }

  public static int getGameId(String line) {
    Matcher matcher = Pattern
      .compile("\\d+")
      .matcher(line);
    if (matcher.find()) {
      return Integer.parseInt(matcher.group());
    }
    throw new IllegalArgumentException();
  }

  public static List<String> getSets(String line) {
    String[] splitGameIdAndSets = line.split(":");
    String[] sets = splitGameIdAndSets[1].split(";");
    Arrays.parallelSetAll(sets, i -> sets[i].trim());
    return List.of(sets);
  }

  public static List<String> getCubes(String set) {
    String[] cubes = set.split(",");
    Arrays.parallelSetAll(cubes, i -> cubes[i].trim());
    return List.of(cubes);
  }

  public static boolean isValidSet(int red, int green, int blue) {
    return red <= MAX_RED_CUBES &&
           green <= MAX_GREEN_CUBES &&
           blue <= MAX_BLUE_CUBES;
  }
}
