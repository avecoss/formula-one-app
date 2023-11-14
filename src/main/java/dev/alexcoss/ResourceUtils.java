package dev.alexcoss;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

public class ResourceUtils {

    public <T> T fileRead(String filePath, Function<BufferedReader, T> function) {
        Path path = Path.of(filePath);
        Path fileName = path.getFileName();

        boolean fileNotExists = Files.notExists(path);
        if (fileNotExists) {
            throw new RuntimeException("File \"" + fileName + "\" not found");
        }

        try (InputStream inStream = Files.newInputStream(path);
             Reader reader = new InputStreamReader(inStream);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            return function.apply(bufferedReader);

        } catch (IOException e) {
            throw new RuntimeException("Unable to read file " + fileName, e);
        }
    }
}
