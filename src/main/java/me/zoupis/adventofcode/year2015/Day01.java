package me.zoupis.adventofcode.year2015;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.zoupis.common.InputHandler;

public class Day01 {
  private static final Logger LOGGER = LogManager.getLogger(Day01.class);
  private static final InputHandler inputHandler = InputHandler.getInstance();

  public static void main(String[] args) {
    part1();
    part2();
  }

  private static void part1() {
    List<String> allLines = inputHandler.readInputFile("adventofcode/year2015/day01.input");
    long countOpeningParenthesis = allLines.get(0).chars().filter(ch -> ch == '(').count();
    long countClosingParenthesis = allLines.get(0).chars().filter(ch -> ch == ')').count();
    LOGGER.info("Floor: {}", countOpeningParenthesis-countClosingParenthesis);
  }

  private static void part2() {
    List<String> allLines = inputHandler.readInputFile("adventofcode/year2015/day01.input");
    String line = allLines.get(0);
    int currentFloor = 0;
    int positionOfBasement = 0;
    for (int i = 0; i < line.length(); i++) {
      if (line.charAt(i) == '(') currentFloor++;
      if (line.charAt(i) == ')') currentFloor--;
      if (currentFloor == -1) {
        positionOfBasement = i + 1;
        break;
      }
    }
    LOGGER.info("Position: {}", positionOfBasement);
  }
}
