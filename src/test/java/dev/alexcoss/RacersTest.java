package dev.alexcoss;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RacersTest {
    private final String testAbbreviationsPath = "src/test/resources/test_abbreviations.txt";
    private final String testStartPath = "src/test/resources/test_start.log";
    private final String testEndPath = "src/test/resources/test_end.log";
    private final Racers racers = new Racers(testAbbreviationsPath, testStartPath, testEndPath);

    @Test
    void shouldCheckListIsNotNull() {
        List<RacerProfile> racersList = racers.getRacersList();
        assertNotNull(racersList);
    }
    @Test
    void shouldReturnTheSameSize() {
        List<RacerProfile> racersList = racers.getRacersList();
        assertEquals(5, racersList.size());
    }

    @Test
    void shouldBeCheckTwoObjects() {
        List<RacerProfile> racersList = racers.getRacersList();
        RacerProfile profileFromList = racersList.get(0);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
        LocalDateTime start = LocalDateTime.parse("2018-05-24_12:12:12.222", dtf);
        LocalDateTime finish = LocalDateTime.parse("2018-05-24_12:12:12.223", dtf);
        RacerProfile expected = new RacerProfile("ABC", "NAME SURNAME", "CAR", start, finish, Duration.between(start, finish));

        assertEquals(expected, profileFromList);
    }
}