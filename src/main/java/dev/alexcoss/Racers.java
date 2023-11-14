package dev.alexcoss;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Racers {
    private static final int LIMIT = 15;
    private static final int NUMBER_OF_REPEATS = 40;
    private static final String REGEX_SEPARATOR = "_";

    private final List<RacerProfile> racersList;

    public Racers(String abbreviationsPath, String startPath, String endPath) {
        racersList = initializeRacersList(abbreviationsPath, startPath, endPath);
    }

    public void printReport() {
        printWinningRacers();
        System.out.println("-".repeat(NUMBER_OF_REPEATS));
        printSkippedRacers();
    }

    public List<RacerProfile> getRacersList() {
        return racersList;
    }

    private List<RacerProfile> initializeRacersList(String abbreviationsPath, String startPath, String endPath) {
        ResourceUtils resourceUtils = new ResourceUtils();
        return resourceUtils.fileRead(abbreviationsPath, bufferedReader -> bufferedReader.lines()
            .map(line -> createRacerProfile(line, startPath, endPath))
            .sorted(Comparator.comparing(RacerProfile::getBestLapTime))
            .collect(Collectors.toList()));
    }

    private RacerProfile createRacerProfile(String line, String startPath, String endPath) {
        String[] data = line.split(REGEX_SEPARATOR);
        int expectedFields = 3;

        String abbreviation = "empty";
        String name = "empty";
        String description = "empty";
        Duration lapTime = Duration.ZERO;

        if (data.length == expectedFields) {
            abbreviation = data[0];
            name = data[1];
            description = data[2];

            LocalDateTime start = getTime(abbreviation, startPath);
            LocalDateTime finish = getTime(abbreviation, endPath);

            if (!start.equals(LocalDateTime.MIN) && !finish.equals(LocalDateTime.MIN)) {
                lapTime = TimeConverter.calculateTimeDifference(start, finish);
            }else {
                System.out.println("Time error!");
            }
        }
        return new RacerProfile(abbreviation, name, description, lapTime);
    }

    private LocalDateTime getTime(String abbreviation, String path) {
        ResourceUtils resourceUtils = new ResourceUtils();
        return resourceUtils.fileRead(path, bufferedReader -> bufferedReader.lines()
            .filter(line -> line.startsWith(abbreviation))
            .map(line -> line.substring(abbreviation.length()))
            .map(TimeConverter::convertStringToLocalDateTime)
            .findFirst()
            .orElse(LocalDateTime.MIN));
    }

    private void printWinningRacers() {
        racersList.stream()
            .limit(LIMIT)
            .forEach(System.out::println);
    }

    private void printSkippedRacers() {
        racersList.stream()
            .skip(LIMIT)
            .forEach(System.out::println);
    }
}
