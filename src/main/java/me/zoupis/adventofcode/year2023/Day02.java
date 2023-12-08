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
    LOGGER.info(part2(input));
  }

  private static int part1(List<String> input) {
    int sumOfIds = 0;
    for (String line : input) {
      sumOfIds += getValidGameId(line);
    }
    return sumOfIds;
  }

  private static int part2(List<String> input) {
    int sumOfPowers = 0;
    for (String line : input) {
      sumOfPowers += getGamePower(line);
    }
    return sumOfPowers;
  }

  public static int getGamePower(String line) {
    List<String> sets = getSets(line);

    int minimumRedNeeded = 0;
    int minimumGreenNeeded = 0;
    int minimumBlueNeeded = 0;

    for (String set : sets) {
      Cubes cubes = getCubes(set);
      if (cubes.red() > minimumRedNeeded) minimumRedNeeded = cubes.red();
      if (cubes.green() > minimumGreenNeeded) minimumGreenNeeded = cubes.green();
      if (cubes.blue() > minimumBlueNeeded) minimumBlueNeeded = cubes.blue();
    }

    return calculateGamePower(new Cubes(minimumRedNeeded, minimumGreenNeeded, minimumBlueNeeded));
  }

  public static int getValidGameId(String line) {
    List<String> sets = getSets(line);
    boolean gameIsValid = true;

    for (String set : sets) {
      Cubes cubes = getCubes(set);
      if (!isValidSet(cubes)) {
        gameIsValid = false;
        break;
      }
    }

    if (gameIsValid) {
      return getGameId(line);
    }
    return 0;
  }

  private static Cubes getCubes(String set) {
    int red = 0;
    int green = 0;
    int blue = 0;
    List<String> cubes = splitCubes(set);
    for (String cube : cubes) {
      String[] amountAndColor = cube.split(" ");
      if (amountAndColor[1].equals("red")) red += Integer.parseInt(amountAndColor[0]);
      else if (amountAndColor[1].equals("green")) green += Integer.parseInt(amountAndColor[0]);
      else blue += Integer.parseInt(amountAndColor[0]);
    }
    return new Cubes(red, green, blue);
  }

  public record Cubes(int red, int green, int blue) {}

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

  public static List<String> splitCubes(String set) {
    String[] cubes = set.split(",");
    Arrays.parallelSetAll(cubes, i -> cubes[i].trim());
    return List.of(cubes);
  }

  public static boolean isValidSet(Cubes cubes) {
    return cubes.red() <= MAX_RED_CUBES &&
           cubes.green() <= MAX_GREEN_CUBES &&
           cubes.blue() <= MAX_BLUE_CUBES;
  }

  public static int calculateGamePower(Cubes cubes) {
    int power = 1;
    if (cubes.red() != 0) power *= cubes.red();
    if (cubes.green() != 0) power *= cubes.green();
    if (cubes.blue() != 0) power *= cubes.blue();

    return power;
  }
}
