package de.emil.pr3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

class InputReaderTest {

    @Test
    void testReadString_validInput() {
        // Simulate user typing a name
        Scanner scanner = new Scanner("Emil");
        InputReader reader = new InputReader(scanner);

        String result = reader.readString("Enter Name: ");

        assertEquals("Emil", result);
    }

    @Test
    void testReadPositiveInteger_validInput() {
        // Simulate entering the number 5
        Scanner scanner = new Scanner("5");
        InputReader reader = new InputReader(scanner);

        // Updated to the method name in your new InputReader class
        int result = reader.readPositiveInteger();

        assertEquals(5, result);
    }

    @Test
    void testReadPositiveInteger_negativeThenValid() {
        // First invalid (-3), then valid (7)
        Scanner scanner = new Scanner("-3 7");
        InputReader reader = new InputReader(scanner);

        int result = reader.readPositiveInteger();

        assertEquals(7, result);
    }

    @Test
    void testReadPositiveInteger_nonNumberThenValid() {
        // First text, then a valid number
        Scanner scanner = new Scanner("abc 10");
        InputReader reader = new InputReader(scanner);

        int result = reader.readPositiveInteger();

        assertEquals(10, result);
    }
}