package me.zoupis.adventofcode.year2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.zoupis.common.InputHandler;

public class Day08 {
  private static final Logger LOGGER = LogManager.getLogger(Day08.class);
  private static final InputHandler INPUT_HANDLER = InputHandler.getInstance();

  public static void main(String[] args) {
    LOGGER.info(part1());
    LOGGER.info(part2());
  }

  private static long part1() {
    List<String> input = INPUT_HANDLER.readInputFile("adventofcode/year2023/day08.input");

    String instructions = input.get(0);
    Map<String, List<String>> map = generateMap(input);

    return navigate(map, instructions, "AAA","ZZZ");
  }

  private static long part2() {
    List<String> input = INPUT_HANDLER.readInputFile("adventofcode/year2023/day08.input");

    String instructions = input.get(0);
    Map<String, List<String>> map = generateMap(input);

    List<String> startingLocations = map.keySet().stream().filter(s -> s.charAt(2) == 'A').toList();

    List<Long> steps = new ArrayList<>();

    for (String startingLocation : startingLocations) {
      steps.add(navigate(map, instructions, startingLocation, null));
    }

    Long[] longs = new Long[steps.size()];
    return calculateLCM(steps.toArray(longs));
  }

  private static long navigate(Map<String, List<String>> map, String instructions, String currentLocation, String finalDestination) {
    int steps = 0;

    while (!isFinalDestination(currentLocation, finalDestination)) {
      for (String instruction : instructions.split("")) {
        List<String> options = map.get(currentLocation);
        if (instruction.equals("L")) {
          currentLocation = options.get(0);
        } else if (instruction.equals("R")) {
          currentLocation = options.get(1);
        }
        steps++;
        if (isFinalDestination(currentLocation, finalDestination)) {
          break;
        }
      }
    }

    return steps;
  }

  public static boolean isFinalDestination(String currentLocation, String finalDestination) {
    if (finalDestination != null) {
      return currentLocation.equals(finalDestination);
    } else {
      return currentLocation.charAt(2) == 'Z';
    }
  }

  private static Map<String, List<String>> generateMap(List<String> input) {
    Map<String, List<String>> map = new HashMap<>();
    String regex = "(.*?) = \\((.*?), (.*?)\\)";
    Pattern pattern = Pattern.compile(regex);

    for (int i = 2; i < input.size(); i++) {
      Matcher matcher = pattern.matcher(input.get(i));
      if (matcher.find()) {
        String origin = matcher.group(1);
        List<String> destinations = List.of(matcher.group(2), matcher.group(3));
        map.put(origin, destinations);
      }
    }
    return map;
  }

  public static long calculateLCM(Long... numbers) {
    long temp = Arrays.stream(numbers).sorted().toList().getLast();

    long largestNumber = temp;

    while (!isDivisibleByAllNumbers(temp, numbers)) {
      temp += largestNumber;
    }

    return temp;
  }

  private static boolean isDivisibleByAllNumbers(long largestNumber, Long[] numbers) {
    boolean result = true;

    for (long number : numbers) {
      if (largestNumber % number != 0) {
        result = false;
        break;
      }
    }

    return result;
  }
}
