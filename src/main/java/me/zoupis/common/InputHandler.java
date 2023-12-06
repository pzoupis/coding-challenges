package me.zoupis.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InputHandler {
  private static final Logger LOGGER = LogManager.getLogger(me.zoupis.common.InputHandler.class);

  private static class SingletonHelper {
    private static final InputHandler INSTANCE = new InputHandler();
  }

  public static InputHandler getInstance() {
    return SingletonHelper.INSTANCE;
  }

  public List<String> readInputFile(String inputFile) {
    List<String> allLines = new ArrayList<>();
    InputStream is = getClass().getClassLoader().getResourceAsStream(inputFile);

    if (is != null) {
      try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
           BufferedReader reader = new BufferedReader(streamReader)) {
        String line;
        while ((line = reader.readLine()) != null) {
          allLines.add(line);
        }
      } catch (IOException e) {
        LOGGER.error("Failed to read file");
      }
    }
    return allLines;
  }
}
