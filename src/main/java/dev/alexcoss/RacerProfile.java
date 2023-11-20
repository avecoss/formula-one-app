package dev.alexcoss;

import java.time.Duration;
import java.util.Objects;

public class RacerProfile {
    private String abbreviations;
    private String fullName;
    private String description;
    private Duration bestLapTime;

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

    public void setAbbreviations(String abbreviations) {
        this.abbreviations = abbreviations;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBestLapTime(Duration bestLapTime) {
        this.bestLapTime = bestLapTime;
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
