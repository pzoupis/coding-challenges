package me.zoupis.adventofcode.year2023;

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
  }

  private static int part1() {
    List<String> input = INPUT_HANDLER.readInputFile("adventofcode/year2023/day08.input");

    String instructions = input.get(0);
    Map<String, List<String>> map = generateMap(input);

    return navigate(map, instructions);
  }

  private static int navigate(Map<String, List<String>> map, String instructions) {
    String currentLocation = "AAA";
    String finalDestination = "ZZZ";
    int steps = 0;
    while (!currentLocation.equals(finalDestination)) {
      for (String instruction : instructions.split("")) {
        var options = map.get(currentLocation);
        if (instruction.equals("L")) {
          currentLocation = options.get(0);
        } else if (instruction.equals("R")) {
          currentLocation = options.get(1);
        }
        steps++;
        if (currentLocation.equals(finalDestination)) {
          break;
        }
      }
    }

    return steps;
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
}
