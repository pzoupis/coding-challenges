package me.zoupis.adventofcode.year2023;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.zoupis.common.InputHandler;

public class Day07 {
  private static final Logger LOGGER = LogManager.getLogger(Day07.class);
  private static final InputHandler INPUT_HANDLER = InputHandler.getInstance();

  public static void main(String[] args) {
    LOGGER.info(part1());
  }

  private static long part1() {
    List<String> input = INPUT_HANDLER.readInputFile("adventofcode/year2023/day07.input");

    Map<Type, List<Map<String, Integer>>> map = new EnumMap<>(Type.class);

    for (String line : input) {
      var lineSplit = line.split("\\s+");

      String hand = lineSplit[0];
      int bid = Integer.parseInt(lineSplit[1]);
      Type type = identifyType(hand);

      Map<String, Integer> cardAndBid = new HashMap<>();
      cardAndBid.put(hand, bid);

      map.computeIfAbsent(type, k -> new ArrayList<>());

      map.get(type).add(cardAndBid);
    }

    return calculateTotalWinnings(map);
  }

  public static long calculateTotalWinnings(Map<Type, List<Map<String, Integer>>> maps) {
    int sum = 0;
    int rank = 1;

    for (Type type : Type.values()) {
      if (maps.containsKey(type)) {
        maps.get(type).sort(new CardComparator());
        for (Map<String, Integer> card : maps.get(type)) {
          sum += card.values().stream().mapToInt(Integer::valueOf).sum() * rank;
          rank++;
        }
      }
    }
    return sum;
  }

  public static Type identifyType(String hand) {
    var map = frequencyMap(hand.chars().mapToObj(c -> (char) c));

    return switch (map.size()) {
      case 1 -> Type.FIVE_OF_A_KIND;
      case 2 -> {
        if (map.containsValue(4L)) yield Type.FOUR_OF_A_KIND;
        else yield Type.FULL_HOUSE;
      }
      case 3 -> {
        if (map.containsValue(3L)) yield Type.THREE_OF_A_KIND;
        else yield Type.TWO_PAIR;
      }
      case 4 -> Type.ONE_PAIR;
      default -> Type.HIGH_CARD;
    };
  }

  public static <C> Map<C, Long> frequencyMap(Stream<C> elements) {
    return elements.collect(
      Collectors.groupingBy(
        Function.identity(),
        Collectors.counting()
      )
    );
  }

  public enum Type {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND
  }

  public enum CardStrength {
    T,
    J,
    Q,
    K,
    A
  }

  static class CardComparator implements Comparator<Map<String, Integer>> {

    @Override
    public int compare(Map<String, Integer> o1, Map<String, Integer> o2) {
      String key1 = o1.keySet().stream().toList().get(0);
      String key2 = o2.keySet().stream().toList().get(0);

      for (int i = 0; i < 5; i++) {
        if (key1.charAt(i) == key2.charAt(i)) {
          continue;
        }

        if (Character.isDigit(key1.charAt(i)) && Character.isDigit(key2.charAt(i))) {
          return key1.compareTo(key2);
        } else if (Character.isDigit(key1.charAt(i)) && !Character.isDigit(key2.charAt(i))) {
          return -1;
        } else if (!Character.isDigit(key1.charAt(i)) && Character.isDigit(key2.charAt(i))) {
          return 1;
        }

        CardStrength c1 = CardStrength.valueOf(String.valueOf(key1.charAt(i)));
        CardStrength c2 = CardStrength.valueOf(String.valueOf(key2.charAt(i)));

        return c1.compareTo(c2);
      }
      throw new IllegalArgumentException();
    }
  }
}
