package dev.alexcoss;

import dev.alexcoss.properties.ColorScheme;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeConverter {
    private static final String FORMATTER = "yyyy-MM-dd_HH:mm:ss.SSS";

    public static LocalDateTime convertStringToLocalDateTime(String time) {
        LocalDateTime convertedTime = LocalDateTime.MIN;
        try {
            convertedTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(FORMATTER));
        } catch (DateTimeParseException e) {
            System.out.println(ColorScheme.RED.getColor() + "Error: Time conversion error" + ColorScheme.DEFAULT.getColor());
        }
        return convertedTime;
    }

    public static Duration calculateTimeDifference(LocalDateTime minuend, LocalDateTime subtrahend) {
        return Duration.between(minuend, subtrahend);
    }
}
