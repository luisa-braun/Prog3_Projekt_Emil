package UI;

import java.util.InputMismatchException;

public class MainMenu extends UserInterface{


    private static final int VIEW_EDIT_WORKERS = 1;
    private static final int VIEW_WORKSCHEDULE = 2;
    private static final int GERENERATE_WORKSCHEDULE = 3;
    private static final int END_PROGRAM = 4;

    /**
     * Starts the command line user interface.
     */
    @Override
    public void start() {
        int funktion = 0;

        do {
            try {
                showMenu();
                funktion = readFunction();
                executeFunction(funktion);
            }
            catch(IllegalArgumentException | InputMismatchException e)
            {
                System.out.println(e);
                e.printStackTrace(System.out);
            }
            catch(Exception e)
            {
                System.out.println(e);
                e.printStackTrace(System.out);
            }
        }
        while (funktion != END_PROGRAM);

    }

    /**
     * Reads the user's selected function.
     *
     * @return The selected function as an integer.
     */
    private int readFunction() {
        return inputReader.readPositivIntegerInput();
    }

    /**
     * Executes the function selected by the user in the main menu.
     *
     */
    private void executeFunction(int funktion) {
        switch (funktion) {
            case VIEW_EDIT_WORKERS:
                //PlaceHolderWorkers();
                break;
            case VIEW_WORKSCHEDULE:
                //PlaceHolderSchedule();
                break;
            case GERENERATE_WORKSCHEDULE:
                //PlaceGolderSchedule1();
                break;
            case END_PROGRAM:
                System.out.println("You have finished the Programm. Goodbye.");
                break;
            default:
                System.out.println("Invalid option selected.");
                break;
        }
    }


    private void showMenu() {
        System.out.println("===== Main Menu =====");
        System.out.println("1. View/Edit Workers");
        System.out.println("2. View Work schedule");
        System.out.println("3. Generate Weekly Work schedule");
        System.out.println("4. End Programm");
        System.out.print(" Please select an option: ");

    }
}
