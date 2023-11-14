package dev.alexcoss;

import java.time.Duration;
import java.util.Objects;

public class RacerProfile {
    private final String abbreviations;
    private final String fullName;
    private final String description;
    private final Duration bestLapTime;

    public RacerProfile(String abbreviations, String fullName, String description, Duration bestLapTime) {
        this.abbreviations = abbreviations;
        this.fullName = fullName;
        this.description = description;
        this.bestLapTime = bestLapTime;
    }

    public String getAbbreviations() {
        return abbreviations;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public Duration getBestLapTime() {
        return bestLapTime;
    }

    @Override
    public String toString() {
        return fullName + " | " + description + " | " + formattedBestLapTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RacerProfile that = (RacerProfile) o;
        return Objects.equals(abbreviations, that.abbreviations) && Objects.equals(fullName, that.fullName)
            && Objects.equals(description, that.description) && Objects.equals(bestLapTime, that.bestLapTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(abbreviations, fullName, description, bestLapTime);
    }

    private String formattedBestLapTime() {
        long minutes = bestLapTime.toMinutes();
        long seconds = bestLapTime.toSecondsPart();
        long millis = bestLapTime.toMillisPart();

        return String.format("%d:%02d.%03d", minutes, seconds, millis);
    }
}
