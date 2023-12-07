package me.zoupis.adventofcode.year2023;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.zoupis.common.InputHandler;

/**
 * Advent of code 2023 <a href="https://adventofcode.com/2023/day/1">Day 1</a>
 */
public class Day01 {
  private static final Logger LOGGER = LogManager.getLogger(Day01.class);
  private static final InputHandler INPUT_HANDLER = InputHandler.getInstance();

  public static void main(String[] args) {
    part1();
    part2();
  }

  public static void part1() {
    List<String> input = INPUT_HANDLER.readInputFile("adventofcode/year2023/day01.input");

    int sum = 0;

    for (String line : input) {
      List<Integer> firstAndLastDigits = getDigits(line);

      int firstNumber = firstAndLastDigits.getFirst();
      int lastNumber = firstAndLastDigits.getLast();
      int calibrationValue = firstNumber * 10 + lastNumber;

      sum += calibrationValue;
    }

    LOGGER.info("The sum of all calibration values is: {}", sum);
  }

  public static void part2() {
    List<String> input = INPUT_HANDLER.readInputFile("adventofcode/year2023/day01.input");

    int sum = 0;

    for (String line : input) {
      List<Integer> firstAndLastDigits = getDigitsSpelledOutWithLetters(line);

      int firstNumber = firstAndLastDigits.getFirst();
      int lastNumber = firstAndLastDigits.getLast();
      int calibrationValue = firstNumber * 10 + lastNumber;

      sum += calibrationValue;
    }

    LOGGER.info("The sum of all calibration values is: {}", sum);
  }

  public static List<Integer> getDigits(String line) {
    List<Integer> digits = new ArrayList<>();

    String regex = "(\\d)";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(line);

    while (matcher.find()) {
      String digit = matcher.group();
      digits.add(Integer.valueOf(digit));
    }

    return digits;
  }

  public static List<Integer> getDigitsSpelledOutWithLetters(String line) {
    List<Integer> digits = new ArrayList<>();

    String regex = "(\\d|one|two|three|four|five|six|seven|eight|nine)";
    String reverseRegex = "(\\d|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin)";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(line);

    if (matcher.find()) {
      String digit = matcher.group();
      digits.add(convertStringToDigit(digit));
    }

    pattern = Pattern.compile(reverseRegex);
    matcher = pattern.matcher(new StringBuilder(line).reverse().toString());

    if (matcher.find()) {
      String digit = matcher.group();
      digits.add(convertStringToDigit(new StringBuilder(digit).reverse().toString()));
    }

    return digits;
  }

  public static int convertStringToDigit(String string) {
    return switch (string) {
      case "one", "1" -> 1;
      case "two", "2" -> 2;
      case "three", "3" -> 3;
      case "four", "4" -> 4;
      case "five", "5" -> 5;
      case "six", "6" -> 6;
      case "seven", "7" -> 7;
      case "eight", "8" -> 8;
      default -> 9;
    };
  }
}
