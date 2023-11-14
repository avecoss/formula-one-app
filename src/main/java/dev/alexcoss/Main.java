package dev.alexcoss;

public class Main {
    private static final String START_FILE = "src/main/resources/logs/start.log";
    private static final String FINISH_FILE = "src/main/resources/logs/end.log";
    private static final String ABBREVIATIONS_FILE = "src/main/resources/abbreviations/abbreviations.txt";

    public static void main(String[] args) {
        Racers racers = new Racers(ABBREVIATIONS_FILE, START_FILE, FINISH_FILE);
        racers.printReport();
    }
}