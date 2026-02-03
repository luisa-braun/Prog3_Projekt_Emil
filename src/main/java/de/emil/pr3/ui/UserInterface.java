package de.emil.pr3.ui;

import java.util.Scanner;

public abstract class UserInterface {
    protected final Scanner scanner;
    protected final InputReader inputReader;

    protected UserInterface() {
        this.scanner = new Scanner(System.in);
        this.inputReader = new InputReader(scanner);
    }

    public abstract void start();
}

