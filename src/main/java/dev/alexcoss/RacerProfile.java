package dev.alexcoss;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class RacerProfile {
    private final String abbreviations;
    private final String fullName;
    private final String description;
    private final LocalDateTime startTime;
    private final LocalDateTime finishTime;
    private final Duration bestLapTime;

    public RacerProfile(String abbreviations, String fullName, String description, LocalDateTime startTime, LocalDateTime finishTime, Duration bestLapTime) {
        this.abbreviations = abbreviations;
        this.fullName = fullName;
        this.description = description;
        this.startTime = startTime;
        this.finishTime = finishTime;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
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
            && Objects.equals(description, that.description) && Objects.equals(startTime, that.startTime)
            && Objects.equals(finishTime, that.finishTime) && Objects.equals(bestLapTime, that.bestLapTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(abbreviations, fullName, description, startTime, finishTime, bestLapTime);
    }

    private String formattedBestLapTime() {
        long minutes = bestLapTime.toMinutes();
        long seconds = bestLapTime.toSecondsPart();
        long millis = bestLapTime.toMillisPart();

        return String.format("%d:%02d.%03d", minutes, seconds, millis);
    }
}
