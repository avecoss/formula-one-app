package dev.alexcoss.properties;

public enum ColorScheme {
    RED("\u001b[31m"), DEFAULT("\033[0m");

    private final String color;

    ColorScheme(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
