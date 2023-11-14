package dev.alexcoss;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Racers {
    private static final int LIMIT = 15;
    private static final int NUMBER_OF_REPEATS = 40;
    private static final String REGEX_SEPARATOR = "_";

    private final List<RacerProfile> racersList;
    private final ResourceUtils resourceUtils = new ResourceUtils();

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
        return resourceUtils.fileRead(abbreviationsPath, bufferedReader -> bufferedReader.lines()
            .map(line -> createRacerProfile(line, startPath, endPath))
            .sorted(Comparator.comparing(RacerProfile::getBestLapTime))
            .collect(Collectors.toList()));
    }

    private RacerProfile createRacerProfile(String line, String startPath, String endPath) {
        String[] data = line.split(REGEX_SEPARATOR);
        String abbr = data[0];
        String name = data[1];
        String des = data[2];
        LocalDateTime startTime = getTime(startPath, abbr);
        LocalDateTime finishTime = getTime(endPath, abbr);
        Duration lapTime = Duration.between(startTime, finishTime);

        return new RacerProfile(abbr, name, des, startTime, finishTime, lapTime);
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

    private LocalDateTime getTime(String path, String abbreviation) {
        return resourceUtils.fileRead(path, bufferedReader -> bufferedReader.lines()
            .filter(line -> line.startsWith(abbreviation))
            .map(line -> line.substring(abbreviation.length()))
            .map(time -> LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS")))
            .findFirst()
            .orElse(LocalDateTime.now()));
    }
}
