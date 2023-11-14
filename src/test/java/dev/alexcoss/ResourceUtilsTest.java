package dev.alexcoss;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceUtilsTest {
    private final ResourceUtils resourceUtils = new ResourceUtils();

    @Test
    void shouldReadListFromResourceFile() {
        String path = "src/test/resources/test.txt";
        List<String> verification = resourceUtils.fileRead(path, bufferedReader -> bufferedReader.lines()
            .collect(Collectors.toList()));

        List<String> expected = List.of("test_line_1", "test_line_2", "test_line_3");

        assertEquals(expected, verification);
    }
}