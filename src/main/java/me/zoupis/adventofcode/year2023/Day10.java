package me.zoupis.adventofcode.year2023;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.zoupis.common.InputHandler;

public class Day10 {
  private static final Logger LOGGER = LogManager.getLogger(Day10.class);
  private static final InputHandler INPUT_HANDLER = InputHandler.getInstance();

  public static void main(String[] args) {
    var input = INPUT_HANDLER.readInputFile("adventofcode/year2023/day10.input");

    LOGGER.info(part1(input));
  }

  private static int part1(List<String> input) {
    var startPoint = getStartingPosition(input);

    if (startPoint == null) {
      return 0;
    }

    var tempPoints = new ArrayList<>(getPotentialNextPositions(startPoint));
    int steps = 1;
    Point invalidPoint = new Point(-1, -1);
    List<Point> previousPoints = new ArrayList<>(List.of(
      startPoint,
      startPoint,
      startPoint,
      startPoint
    ));
    ArrayList<Point> nextPoints = new ArrayList<>();
    List<Point> pointsToBeRemoved = new ArrayList<>();

    while (!isFarthestPoint(tempPoints)) {
      nextPoints = new ArrayList<>();
      for (int i = 0; i < tempPoints.size(); i++) {
        if (!tempPoints.get(i).equals(invalidPoint)) {
          var point = getNextPosition(String.valueOf(input.get(tempPoints.get(i).y()).charAt(tempPoints.get(i).x())), previousPoints.get(i), tempPoints.get(i));
          if (!point.equals(invalidPoint)) {
            nextPoints.add(point);
          } else {
            pointsToBeRemoved.add(tempPoints.get(i));
          }
        }
      }

      tempPoints.removeAll(pointsToBeRemoved);
      pointsToBeRemoved.clear();
      previousPoints = tempPoints;
      tempPoints = nextPoints;
      steps++;
    }
    return steps;
  }

  public static boolean isFarthestPoint(List<Point> points) {
    if (points.size() == 2) {
      return points.get(0).equals(points.get(1));
    } else {
      return false;
    }
  }

  public static List<Point> getPotentialNextPositions(Point startingPoint) {
    return List.of(new Point(startingPoint.x() + 1, startingPoint.y()),
                   new Point(startingPoint.x(), startingPoint.y() + 1),
                   new Point(startingPoint.x() - 1, startingPoint.y()),
                   new Point(startingPoint.x(), startingPoint.y() - 1));
  }

  public static Point getStartingPosition(List<String> input) {
    Point startingPoint = null;
    for (int i = 0; i < input.size(); i++) {
      if (input.get(i).contains("S")) {
        startingPoint = new Point(input.get(i).indexOf("S"), i);
      }
    }
    return startingPoint;
  }

  public static Point getNextPosition(String symbol, Point previousPoint, Point currentPoint) {
    return switch (symbol) {
      case "|" -> getLocationAfterVerticalPipe(previousPoint, currentPoint);
      case "-" -> getLocationAfterHorizontalPipe(previousPoint, currentPoint);
      case "L" -> getLocationAfterLPipe(previousPoint, currentPoint);
      case "J" -> getLocationAfterJPipe(previousPoint, currentPoint);
      case "7" -> getLocationAfter7Pipe(previousPoint, currentPoint);
      case "F" -> getLocationAfterFPipe(previousPoint, currentPoint);
      default -> new Point(-1, -1);
    };
  }

  public record Point (int x, int y) {}

  public static Point getLocationAfterHorizontalPipe(Point previous, Point current) {
    int newX = -1;
    int newY = -1;
    if (previous.x() + 1 == current.x()) {
      newY = current.y();
      newX = current.x() + 1;
    } else if (previous.x() - 1 == current.x()) {
      newY = current.y();
      newX = current.x() - 1;
    }
    return new Point(newX, newY);
  }

  public static Point getLocationAfterVerticalPipe(Point previous, Point current) {
    int newX = -1;
    int newY = -1;
    if (previous.y() + 1 == current.y()) {
      newX = current.x();
      newY = current.y() + 1;
    } else if (previous.y() - 1 == current.y()) {
      newX = current.x();
      newY = current.y() - 1;
    }
    return new Point(newX, newY);
  }

  public static Point getLocationAfterLPipe(Point previous, Point current) {
    int newX = -1;
    int newY = -1;
    if (current.x() == previous.x() - 1 && current.y() == previous.y()) {
      newX = current.x();
      newY = current.y() - 1;
    } else if (current.x() == previous.x() && current.y() == previous.y() + 1) {
      newX = current.x() + 1;
      newY = current.y();
    }
    return new Point(newX, newY);
  }

  public static Point getLocationAfterJPipe(Point previous, Point current) {
    int newX = -1;
    int newY = -1;
    if (current.x() == previous.x() + 1 && current.y() == previous.y()) {
      newX = current.x();
      newY = current.y() - 1;
    } else if (current.x() == previous.x() && current.y() == previous.y() + 1) {
      newX = current.x() - 1;
      newY = current.y();
    }
    return new Point(newX, newY);
  }

  public static Point getLocationAfter7Pipe(Point previous, Point current) {
    int newX = -1;
    int newY = -1;
    if (current.x() == previous.x() + 1 && current.y() == previous.y()) {
      newX = current.x();
      newY = current.y() + 1;
    } else if (current.x() == previous.x() && current.y() == previous.y() - 1) {
      newX = current.x() - 1;
      newY = current.y();
    }
    return new Point(newX, newY);
  }

  public static Point getLocationAfterFPipe(Point previous, Point current) {
    int newX = -1;
    int newY = -1;
    if (current.x() == previous.x() - 1 && current.y() == previous.y()) {
      newX = current.x();
      newY = current.y() + 1;
    } else if (current.x() == previous.x() && current.y() == previous.y() - 1) {
      newX = current.x() + 1;
      newY = current.y();
    }
    return new Point(newX, newY);
  }
}
