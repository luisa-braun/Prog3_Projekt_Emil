package de.emil.pr3;

import java.util.Scanner;
import java.util.NoSuchElementException;

public class InputReader {

    private final Scanner input;

    public InputReader(Scanner input) {
        this.input = input;
    }

    int readPositivIntegerInput() {

        int result = -1;
        String line;
        while(result < 0) {
            try {
                line = input.next();
                result = Integer.parseInt(line);
                if(result < 0)
                    System.out.println("You entered a negative integer. Please try again.");
            }
            catch(NumberFormatException e) {
                System.out.println("Error – Please enter an integer value");
            }
            catch(NoSuchElementException | IllegalStateException e) {
                System.out.println(e);
            }
        }
        return result;
    }
}
