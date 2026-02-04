package de.emil.pr3.ui;

import java.util.Scanner;

public class InputReader {
    private final Scanner scanner;

    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }
    public int readPositiveInteger() {
        int result = -1;
        while (result < 0) {
            try {
                String line = scanner.next();
                result = Integer.parseInt(line);
                if (result < 0) {
                    System.out.println("Please enter a positive Number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid Number.");
            }
        }
        return result;
    }
}