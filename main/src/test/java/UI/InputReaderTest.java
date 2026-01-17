package UI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
class InputReaderTest {

    Scanner scanner;

    @Test
    void testReadPositiveInteger_validInput() {
        // Simulierte Benutzereingabe
        Scanner scanner = new Scanner("5");
        InputReader reader = new InputReader(scanner);

        int result = reader.readPositivIntegerInput();

        assertEquals(5, result);
    }

    @Test
    void testReadPositiveInteger_negativeThenValid() {
        // Erst ungültig (-3), dann gültig (7)
        Scanner scanner = new Scanner("-3 7");
        InputReader reader = new InputReader(scanner);

        int result = reader.readPositivIntegerInput();

        assertEquals(7, result);
    }

    @Test
    void testReadPositiveInteger_nonNumberThenValid() {
        // Erst Text, dann Zahl
        Scanner scanner = new Scanner("abc 10");
        InputReader reader = new InputReader(scanner);

        int result = reader.readPositivIntegerInput();

        assertEquals(10, result);
    }
}
