package dev.alexcoss;

import dev.alexcoss.properties.ColorScheme;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Racers {
    private static final int LIMIT = 15;
    private static final int NUMBER_OF_REPEATS = 40;
    private static final String REGEX_SEPARATOR = "_";
    private static final String EMPTY_STRING = "empty";

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
            .filter(this::isValidRacer)
            .sorted(Comparator.comparing(RacerProfile::getBestLapTime))
            .collect(Collectors.toList()));
    }

    private RacerProfile createRacerProfile(String line, String startPath, String endPath) {
        RacerProfile racerProfile = new RacerProfile();

        try (Scanner scanner = new Scanner(line)) {
            scanner.useDelimiter(REGEX_SEPARATOR);

            setField(scanner, racerProfile::setAbbreviations, "abbreviations");
            setField(scanner, racerProfile::setFullName, "full name");
            setField(scanner, racerProfile::setDescription, "description");
        } catch (NoSuchElementException e) {
            printErrorMessage("Error: Invalid field");
        }

        Duration lapTime = calculateLapTime(racerProfile.getAbbreviations(), startPath, endPath);
        racerProfile.setBestLapTime(lapTime);

        return racerProfile;
    }

    private void setField(Scanner scanner, Consumer<String> setter, String fieldName) {
        if (scanner.hasNext())
            setter.accept(scanner.next());
        else {
            setter.accept(EMPTY_STRING);
            printErrorMessage("Error: Invalid field " + fieldName);
        }
    }

    private Duration calculateLapTime(String abbreviation, String startPath, String endPath) {
        if (EMPTY_STRING.equalsIgnoreCase(abbreviation))
            return Duration.ZERO;

        LocalDateTime start = getTime(abbreviation, startPath);
        LocalDateTime finish = getTime(abbreviation, endPath);

        if (!start.equals(LocalDateTime.MIN) && !finish.equals(LocalDateTime.MIN)) {
            return TimeConverter.calculateTimeDifference(start, finish);
        } else {
            printErrorMessage("Error: Incorrect time calculated");
            return Duration.ZERO;
        }
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

    private boolean isValidRacer(RacerProfile racer) {
        return !racer.getAbbreviations().equalsIgnoreCase(EMPTY_STRING) &&
            !racer.getFullName().equalsIgnoreCase(EMPTY_STRING) &&
            !racer.getDescription().equalsIgnoreCase(EMPTY_STRING) &&
            racer.getBestLapTime().compareTo(Duration.ZERO) > 0;
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

    private void printErrorMessage(String message) {
        System.out.println(ColorScheme.RED.getColor() + message + ColorScheme.DEFAULT.getColor());
    }
}
