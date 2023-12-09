package me.zoupis.adventofcode.year2023;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.zoupis.common.InputHandler;

public class Day09 {
  private static final Logger LOGGER = LogManager.getLogger(Day09.class);
  private static final InputHandler INPUT_HANDLER = InputHandler.getInstance();

  public static void main(String[] args) {
    List<String> input = INPUT_HANDLER.readInputFile("adventofcode/year2023/day09.input");

    LOGGER.info(part1(input));
    LOGGER.info(part2(input));
  }

  public static long part1(List<String> input) {
    long sum = 0;
    for (String line : input) {
      var sequences = getSequences(line);
      sum += predictNextValue(sequences);
    }

    return sum;
  }

  public static long part2(List<String> input) {
    long sum = 0;
    for (String line : input) {
      var sequences = getSequences(line);
      sum += predictPreviousValue(sequences);
    }

    return sum;
  }

  public static long predictNextValue(List<List<Integer>> sequences) {
    long sum = 0;
    for (List<Integer> sequence : sequences) {
      sum += sequence.getLast();
    }
    return sum;
  }

  public static long predictPreviousValue(List<List<Integer>> sequences) {
    long previousValue = 0;
    for (int i = sequences.size() - 1; i >= 0; i--) {
      previousValue = sequences.get(i).getFirst() - previousValue;
    }
    return previousValue;
  }

  public static List<List<Integer>> getSequences(String line) {
    List<List<Integer>> sequences = new ArrayList<>();

    var history = getNumbersFromLine(line);
    sequences.add(history);
    List<Integer> newSequence = history;
    do {
      newSequence = calculateNextSequence(newSequence);
      sequences.add(newSequence);
    } while(!isFinalSequence(newSequence));

    return sequences;
  }

  public static List<Integer> calculateNextSequence(List<Integer> sequence) {
    return IntStream.range(0, sequence.size() - 1)
      .map(i -> sequence.get(i + 1) - sequence.get(i))
      .boxed()
      .toList();
  }

  public static boolean isFinalSequence(List<Integer> sequence) {
    return sequence.stream().allMatch(n -> n == 0);
  }

  public static List<Integer> getNumbersFromLine(String line) {
    var items = line.split(" ");
    return Stream.of(items).mapToInt(Integer::valueOf).boxed().toList();
  }
}
