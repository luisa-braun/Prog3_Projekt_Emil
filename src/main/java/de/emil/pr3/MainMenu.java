package de.emil.pr3;



public class MainMenu extends UserInterface{


    private static final int VIEW_WORKERS = 1;
    private static final int ADD_DELETE_WORKERS = 2;
    private static final int VIEW_WORKSCHEDULE = 3;
    private static final int GENERATE_WORKSCHEDULE = 4;
    private static final int END_PROGRAM = 5;

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
            case VIEW_WORKERS:
                //placeHolderWorkers();
                break;
            case ADD_DELETE_WORKERS:
                //placeHolderEditWorkers();
                break;
            case VIEW_WORKSCHEDULE:
                ShiftPlanner.showEmptyPlan();
                break;
            case GENERATE_WORKSCHEDULE:
                //placeHolderSchedule();
                break;
            case END_PROGRAM:
                System.out.println("You have finished the Program. Goodbye.");
                break;
            default:
                System.out.println("Invalid option selected.");
                break;
        }
    }


    private void showMenu() {
        System.out.println("===== Main Menu =====");
        System.out.println("1. View Workers");
        System.out.println("2. Add/Delete Workers");
        System.out.println("3. View Schedule template");
        System.out.println("4. Generate Weekly Work schedule");
        System.out.println("5. End Program");
        System.out.print(" Please select an option: ");

    }
}
