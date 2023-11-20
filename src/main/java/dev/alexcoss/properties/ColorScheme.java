package dev.alexcoss.properties;

public enum ColorScheme {
    RED("\u001b[31m"), BLUE("\u001B[34m"), GREEN("\u001B[32m"), DEFAULT("\033[0m");

    private final String color;

    ColorScheme(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
