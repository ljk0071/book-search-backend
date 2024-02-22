package com.booksearch.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    private FileUtils() {
    }

    public static List<String> readWholeFile(Path path) throws IOException {

        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    public static String getProperty(String name) {
        Path path = Paths.get("./client/rest/src/main/resources/api-key.properties");

        String value = null;

        try {
            value = readWholeFile(path).stream()
                    .filter(v -> v.contains(name))
                    .findAny().orElseThrow(IllegalArgumentException::new)
                    .split("=")[1]
                    .replace(" ", "");
        } catch (IOException | NullPointerException e) {
            throw new IllegalArgumentException("property key를 다시 확인 해 주세요.");
        }

        return value;
    }
}
